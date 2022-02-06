package com.cyecize.demo.api.database;

import javax.sql.DataSource;
import java.util.List;

public interface DatabaseService {

    boolean hasEstablishedConnection();

    void connectToSQLServer(DatabaseConnectDto databaseConnectDto);

    List<String> findAllDatabases();

    void selectDatabase(String selectedDatabase);

    void createDatabase(String databaseName);

    String getSelectedDatabase();

    DataSource getCurrentOrmDataSource();
}
