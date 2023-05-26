package com.sebastian.bestTravel.domain.repositories.jpa;

import com.sebastian.bestTravel.domain.entities.jpa.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}