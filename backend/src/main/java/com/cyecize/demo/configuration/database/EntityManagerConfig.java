package com.cyecize.demo.configuration.database;

import com.cyecize.demo.api.database.DatabaseProvider;
import com.cyecize.demo.api.database.DatabaseService;
import com.cyecize.demo.constants.General;
import lombok.RequiredArgsConstructor;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class EntityManagerConfig {

    private final DatabaseService databaseService;

    private final AtomicBoolean appLoaded = new AtomicBoolean(false);

    @Value("${spring.jpa.show-sql:false}")
    private final boolean showSQL;

    @EventListener(ApplicationReadyEvent.class)
    public void applicationLoaded() {
        this.appLoaded.set(true);
    }

    /**
     * Create a data source that provides the flexibility to swap multiple data sources
     * based on the business requirements.
     *
     * @return routing data source
     */
    @Bean
    public DataSource createRoutingDataSource() {

        //Provide a default datasource which will be used while the app is starting up.
        final JdbcDataSource defaultDataSource = new JdbcDataSource();
        defaultDataSource.setUrl("jdbc:h2:~/test");
        defaultDataSource.setUser("sa");
        defaultDataSource.setPassword("password");

        return new RoutingDataSource(this.databaseService, defaultDataSource);
    }

    /**
     * Create a proxy of {@link EntityManagerFactory} and supplies real instances based on the current session's config.
     * This is required because spring data / hibernate is bound to one entity manager factory and this is a problem
     * for data sources with different dialects.
     *
     * @param routingDataSource -
     * @return entity manager factory.
     */
    @Bean("entityManagerFactory")
    public EntityManagerFactory createEmf(DataSource routingDataSource) {
        final Map<DatabaseProvider, EntityManagerFactory> entityManagerFactoryMap = new HashMap<>();
        for (DatabaseProvider provider : DatabaseProvider.values()) {
            entityManagerFactoryMap.put(provider, this.createEntityManagerFactory(
                    provider.getDialectClass(),
                    provider.name(),
                    routingDataSource
            ));
        }

        //Provide a default emf which will be used if there is no connection for a given session.
        final EntityManagerFactory defaultEmf = this.createEntityManagerFactory(
                H2Dialect.class.getName(),
                "DefaultH2Factory",
                routingDataSource
        );

        final Object emf = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{EntityManagerFactory.class},
                (proxy, method, args) -> {
                    Object instance = defaultEmf;

                    Optional<DatabaseProvider> databaseProvider = this.databaseService.getCurrentDatabaseProvider();
                    if (databaseProvider.isPresent()) {
                        instance = entityManagerFactoryMap.get(databaseProvider.get());
                    }

                    return method.invoke(instance);
                }
        );

        return (EntityManagerFactory) emf;
    }

    private EntityManagerFactory createEntityManagerFactory(String dialectClassName,
                                                            String providerName,
                                                            DataSource routingDataSource) {
        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        final Properties properties = new Properties() {{
            put("hibernate.physical_naming_strategy", new CamelCaseToUnderscoresNamingStrategy());
            put("hibernate.implicit_naming_strategy", new SpringImplicitNamingStrategy());
        }};

        properties.put("hibernate.dialect", dialectClassName);

        factory.setJpaProperties(properties);
        factory.setPackagesToScan(General.BASE_PACKAGE_NAME);

        factory.setDataSource(routingDataSource);

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(this.showSQL);

        factory.setJpaVendorAdapter(vendorAdapter);

        factory.afterPropertiesSet();
        factory.setPersistenceUnitName(providerName + "Unit");
        factory.setBeanName(providerName + "Bean");

        return factory.getObject();
    }

    class RoutingDataSource extends AbstractRoutingDataSource {
        private final DatabaseService databaseService;
        private final DataSource defaultDataSource;

        RoutingDataSource(DatabaseService databaseService, DataSource defaultDataSource) {
            this.databaseService = databaseService;
            this.defaultDataSource = defaultDataSource;
            super.setDefaultTargetDataSource(defaultDataSource);
            super.setTargetDataSources(new HashMap<>());
        }

        @Override
        protected Object determineCurrentLookupKey() {
            return null;
        }

        @Override
        protected DataSource determineTargetDataSource() {
            if (!appLoaded.get()) {
                return this.defaultDataSource;
            }

            return this.databaseService.getCurrentOrmDataSource();
        }
    }
}
