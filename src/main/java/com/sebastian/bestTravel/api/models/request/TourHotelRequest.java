package com.sebastian.bestTravel.api.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class TourHotelRequest {
    @Positive
    @NotNull(message = "Id hotel is mandatory")
    private Long id;
    @Positive
    @NotNull(message = "Total days is mandatory")
    private Integer totalDays;
}