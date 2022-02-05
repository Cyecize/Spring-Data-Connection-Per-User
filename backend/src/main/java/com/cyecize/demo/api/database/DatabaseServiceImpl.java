package com.cyecize.demo.api.database;

import com.cyecize.demo.api.session.Session;
import com.cyecize.demo.api.session.SessionStorageService;
import com.cyecize.demo.error.ApiException;
import com.cyecize.demo.error.NoSessionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private static final String DATABASE_SESSION_ATTR_NAME = "database";

    private final SessionStorageService sessionStorageService;

    @Override
    public boolean hasEstablishedConnection() {
        final Database database = this.getDatabase();

        return database.getJdbcDataSource() != null || database.getOrmDataSource() != null;
    }

    @Override
    public void connectToDatabase(DatabaseConnectDto databaseConnectDto) {
        final Database database = this.getDatabase();
        this.clearOldConnection(database);

        database.setDatabaseProvider(databaseConnectDto.getDatabaseProvider());
        try {
            this.setJdbcConnection(database, databaseConnectDto);
        } catch (Exception ex) {
            throw new ApiException("Error while connecting to database.");
        }
    }

    private void setJdbcConnection(Database database, DatabaseConnectDto databaseConnectDto) {
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

        final HikariDataSource jdbcDataSource = new HikariDataSource(config);
        database.setJdbcDataSource(jdbcDataSource);
    }

    private void clearOldConnection(Database database) {
        try {
            database.close();
        } catch (Exception e) {
            log.error("Error while closing old DB connection!", e);
            throw new ApiException("Error while closing old DB connection!");
        }
    }

    private Database getDatabase() {
        final Optional<Session> currentSession = this.sessionStorageService.getCurrentSession();
        if (currentSession.isEmpty()) {
            throw new NoSessionException();
        }

        currentSession.get().getSessionStorage().putIfAbsent(DATABASE_SESSION_ATTR_NAME, new Database());

        return (Database) currentSession.get().getSessionStorage().get(DATABASE_SESSION_ATTR_NAME);
    }
}
