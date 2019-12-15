package com.sample.servicename.conf;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantFilter implements ContainerRequestFilter {

  @Value("${multitenant.tenantKey}")
  String tenantKey;

  @Value("${multitenant.defaultTenant}")
  String defaultTenant;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String tenant = requestContext.getHeaderString(tenantKey);
    if (tenant != null) {
      requestContext.setProperty(tenantKey, tenant);
    } else {
      requestContext.setProperty(tenantKey, defaultTenant);
    }
  }
}
