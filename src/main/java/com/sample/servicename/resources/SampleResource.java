package com.sample.servicename.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.sample.servicename.entity.Person;
import com.sample.servicename.repository.PersonRepository;

import jersey.repackaged.com.google.common.collect.Lists;

@Path("/sample")
public class SampleResource {

  @Autowired
  private PersonRepository personRepository;

  @GET
  @Produces("application/json")
  public List<Person> testSpringRetry() {
    List<Person> persons = Lists.newArrayList(this.personRepository.findAll());
    return persons;
  }

}
