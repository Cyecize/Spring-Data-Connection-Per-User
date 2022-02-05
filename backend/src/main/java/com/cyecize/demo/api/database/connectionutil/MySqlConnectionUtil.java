package com.cyecize.demo.api.database.connectionutil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlConnectionUtil implements ConnectionUtil {
    @Override
    public String getConnectionString(String host, int port, boolean useSSL) {
        return String.format("jdbc:mysql://%s:%d?useSSL=%s", host, port, useSSL);
    }

    @Override
    public List<String> findAllDatabases(DataSource dataSource) throws SQLException {
        List<String> databaseNames = new ArrayList<>();

        try (Statement selectDbStatement = dataSource.getConnection().createStatement()) {
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
}
