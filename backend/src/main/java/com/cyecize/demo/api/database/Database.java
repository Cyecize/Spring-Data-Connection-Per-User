package com.cyecize.demo.api.database;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;

import javax.sql.DataSource;

@Data
public class Database implements AutoCloseable {

    private DatabaseProvider databaseProvider;

    private HikariConfig dataSourceConfig = new HikariConfig();

    /**
     * Data source which may not contain info about any specific schema.
     */
    private DataSource jdbcDataSource;

    private String selectedDatabase;

    /**
     * Data source used by hibernate.
     */
    private DataSource ormDataSource;

    @Override
    public void close() throws Exception {
        this.databaseProvider = null;
        this.dataSourceConfig = new HikariConfig();
        this.selectedDatabase = null;

        if (this.jdbcDataSource != null) {
            this.jdbcDataSource.getConnection().close();
            this.jdbcDataSource = null;
        }
        
        if (this.ormDataSource != null) {
            this.ormDataSource.getConnection().close();
            this.ormDataSource = null;
        }
    }
}
