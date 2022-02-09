package com.cyecize.demo.api.database;

import com.cyecize.demo.api.session.Session;
import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.error.ApiException;
import com.cyecize.demo.error.NoDatabaseConnectionException;
import com.cyecize.demo.error.NoSessionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private static final String DATABASE_SESSION_ATTR_NAME = "database";

    private final SessionStorageService sessionStorageService;

    private final ModelMapper modelMapper;

    @Override
    public boolean hasEstablishedConnection() {
        final Database database = this.getDatabase();
        return this.hasEstablishedConnection(database);
    }

    private boolean hasEstablishedConnection(Database database) {
        return database.getJdbcDataSource() != null || database.getOrmDataSource() != null;
    }

    @Override
    public void connectToSQLServer(DatabaseConnectDto databaseConnectDto) {
        final Database database = this.getDatabase();
        this.clearOldConnection(database);

        database.setDatabaseProvider(databaseConnectDto.getDatabaseProvider());
        database.setServerConnectionProperties(this.modelMapper.map(databaseConnectDto, ServerConnectionProperties.class));

        this.setHikariConfig(database, databaseConnectDto);

        try {
            final HikariDataSource jdbcDataSource = new HikariDataSource(database.getDataSourceConfig());
            database.setJdbcDataSource(jdbcDataSource);
        } catch (Exception ex) {
            throw new ApiException("Error while connecting to database.");
        }
    }

    private void clearOldConnection(Database database) {
        try {
            database.close();
        } catch (Exception e) {
            log.error("Error while closing old DB connection!", e);
            throw new ApiException("Error while closing old DB connection!");
        }
    }

    private void setHikariConfig(Database database, DatabaseConnectDto databaseConnectDto) {
        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl(database.getDatabaseProvider().getConnectionUtil().getConnectionString(
                databaseConnectDto.getHost(),
                databaseConnectDto.getPort(),
                databaseConnectDto.getUseSSL()
        ));

        config.setUsername(databaseConnectDto.getUsername());
        config.setPassword(databaseConnectDto.getPassword());
        config.setDriverClassName(database.getDatabaseProvider().getSqlDriver());

        config.setAutoCommit(false);

        database.setDataSourceConfig(config);
    }

    @Override
    public List<String> findAllDatabases() {
        final Database database = this.getDatabase();
        if (!this.hasEstablishedConnection(database)) {
            throw new NoDatabaseConnectionException();
        }

        return this.findAllDatabases(database);
    }

    private List<String> findAllDatabases(Database database) {
        final DataSource dataSource = this.getDataSource(database);

        try {
            return database.getDatabaseProvider().getConnectionUtil().findAllDatabases(dataSource);
        } catch (SQLException e) {
            log.error("Error while obtaining list of databases.", e);
            throw new ApiException("Error while obtaining list of databases.");
        }
    }

    @Override
    public void selectDatabase(String selectedDatabase) {
        final Database database = this.getDatabase();
        if (!this.hasEstablishedConnection(database)) {
            throw new NoDatabaseConnectionException();
        }

        final List<String> allDatabases = this.findAllDatabases(database);

        if (!this.databaseNameExists(allDatabases, selectedDatabase)) {
            throw new ApiException("Database name does not exist!");
        }

        final HikariConfig dataSourceConfig = database.getDataSourceConfig();
        final String oldUrl = dataSourceConfig.getJdbcUrl();
        final ServerConnectionProperties serverProperties = database.getServerConnectionProperties();

        dataSourceConfig.setJdbcUrl(database.getDatabaseProvider().getConnectionUtil().getConnectionString(
                serverProperties.getHost(),
                serverProperties.getPort(),
                serverProperties.getUseSSL(),
                selectedDatabase
        ));

        final HikariDataSource ormDataSource = new HikariDataSource(dataSourceConfig);

        if (!database.getDatabaseProvider().getConnectionUtil().hasValidFlywayTable(ormDataSource)) {
            dataSourceConfig.setJdbcUrl(oldUrl);
            ormDataSource.close();
            throw new ApiException("Database is incompatible!");
        }

        try {
            this.executeFlywayMigrations(database.getDatabaseProvider(), ormDataSource);
        } catch (Exception ex) {
            dataSourceConfig.setJdbcUrl(oldUrl);
            ormDataSource.close();
            throw new ApiException("Database is corrupted!");
        }

        database.closeConnections();
        database.setOrmDataSource(ormDataSource);
        database.setSelectedDatabase(selectedDatabase);
    }

    @Override
    public void createDatabase(String databaseName) {
        final Database database = this.getDatabase();
        if (!this.hasEstablishedConnection(database)) {
            throw new NoDatabaseConnectionException();
        }

        final List<String> allDatabases = this.findAllDatabases(database);

        if (this.databaseNameExists(allDatabases, databaseName)) {
            throw new ApiException("Database name already exists!");
        }

        database.getDatabaseProvider().getConnectionUtil().createDatabase(this.getDataSource(database), databaseName);

        final HikariConfig dataSourceConfig = database.getDataSourceConfig();
        final String oldUrl = dataSourceConfig.getJdbcUrl();
        final ServerConnectionProperties serverProperties = database.getServerConnectionProperties();

        dataSourceConfig.setJdbcUrl(database.getDatabaseProvider().getConnectionUtil().getConnectionString(
                serverProperties.getHost(),
                serverProperties.getPort(),
                serverProperties.getUseSSL(),
                databaseName
        ));

        final HikariDataSource ormDataSource = new HikariDataSource(dataSourceConfig);

        try {
            this.executeFlywayMigrations(database.getDatabaseProvider(), ormDataSource);
        } catch (Exception ex) {
            dataSourceConfig.setJdbcUrl(oldUrl);
            ormDataSource.close();
            throw new ApiException("Database is corrupted!");
        }

        database.closeConnections();
        database.setOrmDataSource(ormDataSource);
        database.setSelectedDatabase(databaseName);
    }

    private void executeFlywayMigrations(DatabaseProvider provider, DataSource dataSource) {
        Flyway.configure()
                .dataSource(dataSource)
                .locations(String.format("migrations/%s", provider.getMigrationsFolderName()))
                .load()
                .migrate();
    }

    private boolean databaseNameExists(List<String> allDatabases, String selectedDatabase) {
        return allDatabases.stream().anyMatch(dbName -> dbName.equalsIgnoreCase(selectedDatabase));
    }

    private DataSource getDataSource(Database database) {
        return Objects.requireNonNullElse(database.getJdbcDataSource(), database.getOrmDataSource());
    }

    @Override
    public String getSelectedDatabase() {
        final Database database = this.getDatabase();
        return database.getSelectedDatabase();
    }

    @Override
    public DataSource getCurrentOrmDataSource() {
        final Database database = this.getDatabase();

        if (database.getOrmDataSource() == null) {
            throw new NoDatabaseConnectionException();
        }

        return database.getOrmDataSource();
    }

    @Override
    public Optional<DatabaseProvider> getCurrentDatabaseProvider() {
        return this.getDatabase(this.sessionStorageService.getCurrentSession())
                .map(Database::getDatabaseProvider);
    }

    private Database getDatabase() {
        final Optional<Session> currentSession = this.sessionStorageService.getCurrentSession();
        if (currentSession.isEmpty()) {
            throw new NoSessionException();
        }

        currentSession.get().getSessionStorage().putIfAbsent(DATABASE_SESSION_ATTR_NAME, new Database());

        return (Database) currentSession.get().getSessionStorage().get(DATABASE_SESSION_ATTR_NAME);
    }

    private Optional<Database> getDatabase(Optional<Session> session) {
        if (session.isEmpty()) {
            return Optional.empty();
        }

        session.get().getSessionStorage().putIfAbsent(DATABASE_SESSION_ATTR_NAME, new Database());

        return Optional.of((Database) session.get().getSessionStorage().get(DATABASE_SESSION_ATTR_NAME));
    }
}
