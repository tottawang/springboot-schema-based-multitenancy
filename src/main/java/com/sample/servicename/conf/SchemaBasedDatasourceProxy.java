package com.sample.servicename.conf;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.HibernateException;

public class SchemaBasedDatasourceProxy implements DataSource {

  private DataSource dataSource;
  private HeaderTenantIdentifierResolver headerTenantIdentifierResolver;

  public SchemaBasedDatasourceProxy(DataSource dataSource,
      HeaderTenantIdentifierResolver headerTenantIdentifierResolver) {
    this.dataSource = dataSource;
    this.headerTenantIdentifierResolver = headerTenantIdentifierResolver;
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return dataSource.getLogWriter();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    dataSource.setLogWriter(out);
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    dataSource.setLoginTimeout(seconds);
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return dataSource.getLoginTimeout();
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return dataSource.getParentLogger();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return dataSource.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return dataSource.isWrapperFor(iface);
  }

  @Override
  public Connection getConnection() throws SQLException {
    Connection connection = dataSource.getConnection();
    setSchema(connection);
    return connection;

  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    Connection connection = dataSource.getConnection(username, password);
    setSchema(connection);
    return connection;
  }

  private void setSchema(Connection connection) {
    String tenantIdentifier = headerTenantIdentifierResolver.resolveCurrentTenantIdentifier();
    try {
      connection.setSchema(tenantIdentifier);
    } catch (SQLException e) {
      throw new HibernateException(
          "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
    }
  }

}
