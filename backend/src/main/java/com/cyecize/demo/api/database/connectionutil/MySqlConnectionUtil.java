package com.cyecize.demo.api.database.connectionutil;

import com.cyecize.demo.constants.General;
import com.cyecize.demo.error.ApiException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MySqlConnectionUtil implements ConnectionUtil {
    @Override
    public String getConnectionString(String host, int port, boolean useSSL) {
        return this.getConnectionString(host, port, useSSL, null);
    }

    @Override
    public String getConnectionString(String host, int port, boolean useSSL, String database) {
        return String.format(
                "jdbc:mysql://%s:%d%s?useSSL=%s",
                host,
                port,
                database != null ? "/" + database : "",
                useSSL
        );
    }

    @Override
    public List<String> findAllDatabases(DataSource dataSource) throws SQLException {
        List<String> databaseNames = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement selectDbStatement = connection.createStatement()) {
            final String databaseNameAlias = "Database";

            String queryForGettingDbNames = String.format(
                    "SELECT DISTINCT SCHEMA_NAME AS `%s` FROM information_schema.SCHEMATA "
                            + "WHERE SCHEMA_NAME NOT IN ('information_schema', 'performance_schema', 'mysql') "
                            + "ORDER BY SCHEMA_NAME",
                    databaseNameAlias
            );

            try (ResultSet databaseNamesResultSet = selectDbStatement.executeQuery(queryForGettingDbNames)) {
                while (databaseNamesResultSet.next()) {
                    String dbName = databaseNamesResultSet.getString(databaseNameAlias);
                    databaseNames.add(dbName);
                }
            }

            return databaseNames;
        }
    }

    @Override
    public boolean hasValidFlywayTable(DataSource dataSource) {
        final String fileNameAlias = "Filename";
        final String query = String.format("SELECT script as %s FROM flyway_schema_history LIMIT 1", fileNameAlias);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            resultSet.next();
            String fileName = resultSet.getString(fileNameAlias);
            return General.INITIAL_FLYWAY_FILE_NAME.equals(fileName);

        } catch (SQLException ignored) {
        }

        return false;
    }

    @Override
    public void createDatabase(DataSource dataSource, String databaseName) {
        final String query = String.format("CREATE DATABASE %s", databaseName);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException error) {
            log.error("Error while crating database!", error);
            throw new ApiException("Error while crating database!");
        }
    }
}
