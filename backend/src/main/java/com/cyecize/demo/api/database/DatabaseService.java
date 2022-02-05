package com.cyecize.demo.api.database;

import java.util.List;

public interface DatabaseService {

    boolean hasEstablishedConnection();

    void connectToDatabase(DatabaseConnectDto databaseConnectDto);

    List<String> findAllDatabases();
}
