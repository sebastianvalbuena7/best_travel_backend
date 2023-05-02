package com.sebastian.bestTravel.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class TourResponse {
    private Long id;
    private Set<UUID> ticketIds;
    private Set<UUID> reservationIds;
}