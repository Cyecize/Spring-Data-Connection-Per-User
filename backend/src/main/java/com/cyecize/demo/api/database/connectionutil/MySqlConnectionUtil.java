package com.cyecize.demo.api.database.connectionutil;

public class MySqlConnectionUtil implements ConnectionUtil {
    @Override
    public String getConnectionString(String host, int port, boolean useSSL) {
        return String.format("jdbc:mysql://%s:%d?useSSL=%s", host, port, useSSL);
    }
}
