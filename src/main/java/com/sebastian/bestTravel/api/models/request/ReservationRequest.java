package com.sebastian.bestTravel.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ReservationRequest {
    private String idClient;
    private Long idHotel;
    private Integer totalDays;
}