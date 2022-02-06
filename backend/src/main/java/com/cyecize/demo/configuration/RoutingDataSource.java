package com.cyecize.demo.configuration;

import com.cyecize.demo.api.database.DatabaseService;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;

@Component
public class RoutingDataSource extends AbstractRoutingDataSource {

    private final DatabaseService databaseService;

    private boolean appLoaded = false;

    public RoutingDataSource(DatabaseService databaseService) {
        this.databaseService = databaseService;
        super.setTargetDataSources(new HashMap<>());


        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb");
        ds.setUser("sa");
        ds.setPassword("password");
        super.setDefaultTargetDataSource(ds);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationLoaded() {
        this.appLoaded = true;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        if (!this.appLoaded) {
            return super.getResolvedDefaultDataSource();
        }

        return this.databaseService.getCurrentOrmDataSource();
    }
}