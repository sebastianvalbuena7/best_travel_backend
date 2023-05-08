package com.sebastian.bestTravel.api.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class TicketRequest {
    @Size(min = 18, max = 20, message = "The size have to a length between 10 and 20 characters")
    @NotBlank(message = "Id client is mandatory")
    private String idClient;
    @Positive
    @NotNull(message = "Id fly is mandatory")
    private Long idFly;
    @Email(message = "Invalid email")
    private String email;
}