package com.sample.servicename.conf;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class SampleConfiguration {

  @Autowired
  AutowireCapableBeanFactory beanFactory;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public FilterRegistrationBean myFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    Filter tenantFilter = new MultiTenantFilter();
    beanFactory.autowireBean(tenantFilter);
    registration.setFilter(tenantFilter);
    registration.addUrlPatterns("/*");
    return registration;
  }

}
