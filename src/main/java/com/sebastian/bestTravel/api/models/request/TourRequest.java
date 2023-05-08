package com.sebastian.bestTravel.api.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class TourRequest {
    @Size(min = 18, max = 20, message = "The size have to a length between 10 and 20 characters")
    @NotBlank(message = "Id customer is mandatory")
    private String customerId;
    @Size(min=1, message = "Min flight tour per tour")
    private Set<TourFlyRequest> flights;
    @Size(min = 1, message = "Min hotel tour per tour")
    private Set<TourHotelRequest> hotels;
    @Email(message = "Invalid email")
    private String email;
}