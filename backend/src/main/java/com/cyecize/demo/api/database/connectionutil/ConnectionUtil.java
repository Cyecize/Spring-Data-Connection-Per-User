package com.cyecize.demo.api.database.connectionutil;

public interface ConnectionUtil {

    String getConnectionString(String host, int port, boolean useSSL);
}
