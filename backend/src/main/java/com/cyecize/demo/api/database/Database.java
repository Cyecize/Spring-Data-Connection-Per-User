package com.cyecize.demo.api.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

@Data
public class Database implements AutoCloseable {

    private DatabaseProvider databaseProvider;

    private ServerConnectionProperties serverConnectionProperties;

    private HikariConfig dataSourceConfig = new HikariConfig();

    /**
     * Data source which may not contain info about any specific schema.
     */
    private HikariDataSource jdbcDataSource;

    private String selectedDatabase;

    /**
     * Data source used by hibernate.
     */
    private HikariDataSource ormDataSource;

    @Override
    public void close() {
        this.databaseProvider = null;
        this.dataSourceConfig = new HikariConfig();
        this.selectedDatabase = null;
        this.serverConnectionProperties = null;

        if (this.jdbcDataSource != null) {
            this.jdbcDataSource.close();
            this.jdbcDataSource = null;
        }

        if (this.ormDataSource != null) {
            this.ormDataSource.close();
            this.ormDataSource = null;
        }
    }

    public void closeConnections() {
        try {
            if (this.jdbcDataSource != null) {
                this.jdbcDataSource.close();
            }
        } finally {
            this.jdbcDataSource = null;
        }

        try {
            if (this.ormDataSource != null) {
                this.ormDataSource.close();
            }
        } finally {
            this.ormDataSource = null;
        }
    }
}
