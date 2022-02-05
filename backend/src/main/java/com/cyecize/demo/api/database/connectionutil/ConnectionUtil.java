package com.cyecize.demo.api.database.connectionutil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface ConnectionUtil {

    String getConnectionString(String host, int port, boolean useSSL);

    List<String> findAllDatabases(DataSource dataSource) throws SQLException;
}
