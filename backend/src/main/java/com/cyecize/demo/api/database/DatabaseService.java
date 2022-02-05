package com.cyecize.demo.api.database;

public interface DatabaseService {

    boolean hasEstablishedConnection();

    void connectToDatabase(DatabaseConnectDto databaseConnectDto);
}
