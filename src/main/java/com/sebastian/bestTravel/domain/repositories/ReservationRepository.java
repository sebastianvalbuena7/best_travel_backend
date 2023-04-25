package com.sebastian.bestTravel.domain.repositories;

import com.sebastian.bestTravel.domain.entities.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}