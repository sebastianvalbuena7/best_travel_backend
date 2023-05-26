package com.sebastian.bestTravel.domain.repositories.jpa;

import com.sebastian.bestTravel.domain.entities.jpa.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}