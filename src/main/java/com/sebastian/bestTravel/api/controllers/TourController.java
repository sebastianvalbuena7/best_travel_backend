package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.TourRequest;
import com.sebastian.bestTravel.api.models.response.ErrorsResponse;
import com.sebastian.bestTravel.api.models.response.TourResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tour") // Para swagger UI
public class TourController {
    private final ITourService tourService;

    @ApiResponse(responseCode = "400", description = "When the request have a field invalid we response this", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
    }
    )
    @Operation(summary = "Save in system a tour base in list of hotels and flights") // Descipcion para este endpoint en swagger ui
    @PostMapping
    public ResponseEntity<TourResponse> post(@Valid @RequestBody TourRequest tourRequest) {
        return ResponseEntity.ok(tourService.create(tourRequest));
    }

    @Operation(summary = "Return a Tour with and passed")
    @GetMapping("{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.read(id));
    }

    @Operation(summary = "Delete a Tour with and passed")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove a Ticket from tour")
    @PatchMapping("{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<TourResponse> removeTicket(@PathVariable Long tourId, @PathVariable UUID ticketId) {
        tourService.removeTicket(ticketId, tourId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a Ticket from tour")
    @PatchMapping("{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId, @PathVariable Long flyId) {
        var response = Collections.singletonMap("ticketId", tourService.addTicket(flyId, tourId));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a Reservation from tour")
    @PatchMapping("{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<TourResponse> removeReservation(@PathVariable Long tourId, @PathVariable UUID reservationId) {
        tourService.removeReservation(reservationId, tourId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a Reservation from tour")
    @PatchMapping("{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addReservation(@PathVariable Long tourId, @PathVariable Long hotelId, @RequestParam Integer totalDays) {
        var response = Collections.singletonMap("reservationId", tourService.addReservation(hotelId, tourId, totalDays));
        return ResponseEntity.ok(response);
    }
}