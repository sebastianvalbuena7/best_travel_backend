package com.sebastian.bestTravel.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class TicketResponse {
    private UUID id;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private LocalDate purchaseDate;
    private BigDecimal price;
    private FlyResponse fly;
}