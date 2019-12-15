package com.sample.servicename.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SampleApplicationConfiguration {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public JdbcTemplate centralJdbcTemplate(
      @Autowired HeaderTenantIdentifierResolver headerTenantIdentifierResolver,
      @Autowired DataSource datasource) throws InterruptedException {
    SchemaBasedDatasourceProxy dataSourceProxy =
        new SchemaBasedDatasourceProxy(datasource, headerTenantIdentifierResolver);
    return new JdbcTemplate(dataSourceProxy);
  }

}

