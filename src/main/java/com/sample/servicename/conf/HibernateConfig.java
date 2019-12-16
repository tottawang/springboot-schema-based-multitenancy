package com.sample.servicename.conf;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class HibernateConfig {

  @Bean
  public MultiTenantConnectionProvider multiTenantConnectionProvider(DataSource datasource,
      HeaderTenantIdentifierResolver headerTenantIdentifierResolver) {
    return new SchemaBasedTenantConnectionProvider(
        new SchemaBasedDatasourceProxy(datasource, headerTenantIdentifierResolver));
  }

  @Bean
  public JdbcTemplate centralJdbcTemplate(DataSource datasource,
      HeaderTenantIdentifierResolver headerTenantIdentifierResolver) {
    // TODO create duplicate instance of SchemaBasedDatasourceProxy because
    // SchemaBasedDatasourceProxy bean setup failed
    return new JdbcTemplate(
        new SchemaBasedDatasourceProxy(datasource, headerTenantIdentifierResolver));
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource datasource,
      MultiTenantConnectionProvider multiTenantConnectionProvider,
      CurrentTenantIdentifierResolver tenantIdentifierResolver) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(datasource);
    em.setPackagesToScan("com.sample");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    Map<String, Object> jpaProperties = new HashMap<>();
    jpaProperties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
    jpaProperties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    jpaProperties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
    jpaProperties.put(Environment.FORMAT_SQL, true);
    em.setJpaPropertyMap(jpaProperties);
    return em;
  }

}
