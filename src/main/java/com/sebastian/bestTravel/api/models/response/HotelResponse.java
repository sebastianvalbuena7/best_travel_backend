package com.sebastian.bestTravel.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private BigDecimal price;
}
