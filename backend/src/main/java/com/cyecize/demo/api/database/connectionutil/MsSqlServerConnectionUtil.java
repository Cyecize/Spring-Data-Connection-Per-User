package com.cyecize.demo.api.database.connectionutil;

import javax.sql.DataSource;
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
    public List<String> findAllDatabases(DataSource dataSource) throws SQLException {
        final String databaseAlias = "Database";
        final String queryForGettingAllSchemas = String.format(
                "SELECT [db].name AS [%s] FROM master.sys.databases AS [db]",
                databaseAlias
        );

        List<String> dbNames = new ArrayList<>();

        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(queryForGettingAllSchemas)) {
            while (resultSet.next()) {
                dbNames.add(resultSet.getString(databaseAlias));
            }
        }

        return dbNames;
    }
}
