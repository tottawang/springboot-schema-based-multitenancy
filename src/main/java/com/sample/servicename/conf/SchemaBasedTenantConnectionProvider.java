package com.sample.servicename.conf;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;

public class SchemaBasedTenantConnectionProvider implements MultiTenantConnectionProvider {

  private static final long serialVersionUID = 1L;
  private SchemaBasedDatasourceProxy dataSourceProxy;

  public SchemaBasedTenantConnectionProvider(SchemaBasedDatasourceProxy dataSourceProxy) {
    this.dataSourceProxy = dataSourceProxy;
  }

  @Override
  public Connection getAnyConnection() throws SQLException {
    return dataSourceProxy.getConnection();
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    // tenantIdentifier is resolved in SchemaBasedDatasourceProxy with schema set
    return getAnyConnection();
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection)
      throws SQLException {
    try {
      connection.createStatement().execute("SET search_path to public");
    } catch (SQLException e) {
      throw new HibernateException(
          "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
    }

    connection.close();
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return true;
  }

  @Override
  public boolean isUnwrappableAs(Class unwrapType) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> unwrapType) {
    return null;
  }
}

