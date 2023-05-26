package com.sebastian.bestTravel.domain.repositories.jpa;

import com.sebastian.bestTravel.domain.entities.jpa.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long>{
}