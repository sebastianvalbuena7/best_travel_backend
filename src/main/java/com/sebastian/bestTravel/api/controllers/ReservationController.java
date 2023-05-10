package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.ReservationRequest;
import com.sebastian.bestTravel.api.models.response.ErrorsResponse;
import com.sebastian.bestTravel.api.models.response.ReservationResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.IReservationService;
import com.sebastian.bestTravel.infrastructure.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
@Tag(name = "Reservation")
public class ReservationController {
    private final IReservationService reservationService;

    @ApiResponse(responseCode = "400", description = "When the request have a field invalid we response this", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
    }
    )
    @Operation(summary = "Save in system a reservation")
    @PostMapping
    public ResponseEntity<ReservationResponse> post(@Valid @RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.create(reservationRequest));
    }

    @Operation(summary = "Return a Reservation with a id")
    @GetMapping({"{id}"})
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.read(id));
    }

    @Operation(summary = "Update a Reservation with a id")
    @PutMapping("{id}")
    public ResponseEntity<ReservationResponse> put(@Valid @RequestBody ReservationRequest reservationRequest, @PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.update(reservationRequest, id));
    }

    @Operation(summary = "Delete a Reservation with a id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Return a price of reservation with id")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(@RequestParam UUID reservationId, @RequestHeader(required = false) Currency currency) {
        if (Objects.isNull(currency)) currency = Currency.getInstance("USD");
        return ResponseEntity.ok(Collections.singletonMap("reservationPrice", reservationService.findPrice(reservationId, currency)));
    }
}