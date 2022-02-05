package com.cyecize.demo.api.database.connectionutil;

public class MsSqlServerConnectionUtil implements ConnectionUtil {
    @Override
    public String getConnectionString(String host, int port, boolean useSSL) {
        return String.format("jdbc:sqlserver://%s:%d;encrypt=%s", host, port, useSSL);
    }
}
