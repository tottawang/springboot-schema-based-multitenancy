package com.sample.servicename.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sample.servicename.entity.Person;

@Repository("personRepository")
public interface PersonRepository extends CrudRepository<Person, Long> {
}
