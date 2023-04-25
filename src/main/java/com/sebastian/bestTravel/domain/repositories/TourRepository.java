package com.sebastian.bestTravel.domain.repositories;

import com.sebastian.bestTravel.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long>{
}