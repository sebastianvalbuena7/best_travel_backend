package com.sebastian.bestTravel.domain.repositories;

import com.sebastian.bestTravel.domain.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}