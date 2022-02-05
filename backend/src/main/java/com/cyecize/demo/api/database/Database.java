package com.cyecize.demo.api.database;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;

import javax.sql.DataSource;

@Data
public class Database {

    private DatabaseProvider databaseProvider;

    private HikariConfig dataSourceConfig;

    /**
     * Data source which may not contain info about any specific schema.
     */
    private DataSource jdbcDataSource;

    private String selectedDatabase;

    /**
     * Data source used by hibernate.
     */
    private DataSource ormDataSource;
}
