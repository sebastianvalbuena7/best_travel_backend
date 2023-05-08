package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.TourRequest;
import com.sebastian.bestTravel.api.models.response.TourResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITourService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/tour")
public class TourController {
    private final ITourService tourService;

    @PostMapping
    public ResponseEntity<TourResponse> post(@Valid @RequestBody TourRequest tourRequest) {
        return ResponseEntity.ok(tourService.create(tourRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.read(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<TourResponse> removeTicket(@PathVariable Long tourId, @PathVariable UUID ticketId) {
        tourService.removeTicket(ticketId, tourId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId, @PathVariable Long flyId) {
        var response = Collections.singletonMap("ticketId", tourService.addTicket(flyId, tourId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<TourResponse> removeReservation(@PathVariable Long tourId, @PathVariable UUID reservationId) {
        tourService.removeReservation(reservationId, tourId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addReservation(@PathVariable Long tourId, @PathVariable Long hotelId, @RequestParam Integer totalDays) {
        var response = Collections.singletonMap("reservationId", tourService.addReservation(hotelId, tourId, totalDays));
        return ResponseEntity.ok(response);
    }
}