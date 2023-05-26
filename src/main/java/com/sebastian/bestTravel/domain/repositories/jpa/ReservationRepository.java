package com.sebastian.bestTravel.domain.repositories.jpa;

import com.sebastian.bestTravel.domain.entities.jpa.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}