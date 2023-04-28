package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.ReservationRequest;
import com.sebastian.bestTravel.api.models.response.ReservationResponse;
import com.sebastian.bestTravel.infrastructure.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping
    public ResponseEntity<ReservationResponse> post(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.create(reservationRequest));
    }

    @GetMapping({"{id}"})
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.read(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationResponse> put(@RequestBody ReservationRequest reservationRequest, @PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.update(reservationRequest, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(@RequestParam UUID reservationId) {
        return ResponseEntity.ok(Collections.singletonMap("reservationPrice", reservationService.findPrice(reservationId)));
    }
}