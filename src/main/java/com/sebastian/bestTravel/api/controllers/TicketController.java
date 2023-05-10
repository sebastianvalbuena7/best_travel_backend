package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.TicketRequest;
import com.sebastian.bestTravel.api.models.response.ErrorsResponse;
import com.sebastian.bestTravel.api.models.response.TicketResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITicketService;
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
@RequestMapping("/ticket")
@AllArgsConstructor
@Tag(name = "Ticket")
//@Controller // Se usa cuando se tienen las vistas integradas con un JSP parecido Thymeleaf
public class TicketController {
    public final ITicketService ticketService;

    @ApiResponse(responseCode = "400", description = "When the request have a field invalid we response this", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
    }
    )
    @Operation(summary = "Save in system a ticket")
    @PostMapping
    public ResponseEntity<TicketResponse> post(@Valid @RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.create(ticketRequest));
    }

    @Operation(summary = "Return a Ticket with a id")
    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.read(id));
    }

    @Operation(summary = "Update a Ticket with a id")
    @PutMapping("{id}")
    public ResponseEntity<TicketResponse> put(@Valid @RequestBody TicketRequest request, @PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.update(request, id));
    }

    @Operation(summary = "Delete a Ticket with a id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Return a price of id fly")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long flyId, @RequestHeader(required = false) Currency currency) {
        if (Objects.isNull(currency)) currency = Currency.getInstance("USD");
        return ResponseEntity.ok(Collections.singletonMap("flyPrice", ticketService.findPrice(flyId, currency)));
    }
}