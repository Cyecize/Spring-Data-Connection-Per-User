package com.cyecize.demo.api.database.connectionutil;

import com.cyecize.demo.constants.General;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MsSqlServerConnectionUtil implements ConnectionUtil {
    @Override
    public String getConnectionString(String host, int port, boolean useSSL) {
        return String.format("jdbc:sqlserver://%s:%d;encrypt=%s", host, port, useSSL);
    }

    @Override
    public String getConnectionString(String host, int port, boolean useSSL, String database) {
        String connectionString = this.getConnectionString(host, port, useSSL);
        if (database != null) {
            connectionString = connectionString + ";database=" + database;
        }

        return connectionString;
    }

    @Override
    public List<String> findAllDatabases(DataSource dataSource) throws SQLException {
        final String databaseAlias = "Database";
        final String queryForGettingAllSchemas = String.format(
                "SELECT [db].name AS [%s] FROM master.sys.databases AS [db]",
                databaseAlias
        );

        List<String> dbNames = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queryForGettingAllSchemas)) {
            while (resultSet.next()) {
                dbNames.add(resultSet.getString(databaseAlias));
            }
        }

        return dbNames;
    }

    @Override
    public boolean hasValidFlywayTable(DataSource dataSource) {
        final String fileNameAlias = "Filename";
        final String query = String.format("SELECT top 1 script as %s FROM flyway_schema_history", fileNameAlias);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            resultSet.next();
            String fileName = resultSet.getString(fileNameAlias);
            return General.INITIAL_FLYWAY_FILE_NAME.equals(fileName);

        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }

        return false;
    }
}
