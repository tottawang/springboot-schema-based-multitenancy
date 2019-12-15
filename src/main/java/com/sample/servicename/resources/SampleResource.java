package com.sample.servicename.resources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.sample.servicename.conf.HeaderTenantIdentifierResolver;
import com.sample.servicename.entity.Person;
import com.sample.servicename.repository.PersonRepository;

import jersey.repackaged.com.google.common.collect.Lists;

@Component
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private HeaderTenantIdentifierResolver headerTenantIdentifierResolver;

  @GET
  @Path("test")
  public String test() {
    return "test";
  }

  @GET
  @Path("people")
  public List<Person> findAllPerson() {
    List<Person> persons = Lists.newArrayList(this.personRepository.findAll());
    return persons;
  }

  @GET
  @Path("people2")
  public List<Object> findAllPersonByJdbcTemplate() {
    // this might not be the right schema implementation for jdbcTemplate
    // looks have to change every sql? this is not acceptable..
    String sql = String.format("select * from %s.person",
        headerTenantIdentifierResolver.resolveCurrentTenantIdentifier());
    return this.executeQuery(sql, new RowMapper<Object>() {
      @Override
      public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        // take a look why trim is needed..
        return rs.getObject("first_name").toString().trim();
      }
    });
  }

  public <T> List<T> executeQuery(String sql, RowMapper<T> rowMapper) {
    return jdbcTemplate.query(sql, null, null, rowMapper);
  }
}
