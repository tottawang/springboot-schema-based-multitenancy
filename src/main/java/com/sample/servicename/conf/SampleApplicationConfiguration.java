package com.sample.servicename.conf;

import javax.sql.DataSource;

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
  public JdbcTemplate centralJdbcTemplate(DataSource centralDataSource) {
    return new JdbcTemplate(centralDataSource);
  }

}

