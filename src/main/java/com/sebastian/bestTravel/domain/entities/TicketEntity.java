package com.sebastian.bestTravel.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "ticket")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class TicketEntity {
    @Id
    private UUID id;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private LocalDate purchaseDate;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "fly_id")
    private FlyEntity fly;
    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = true)
    private TourEntity tour;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private CustomerEntity customer;
}