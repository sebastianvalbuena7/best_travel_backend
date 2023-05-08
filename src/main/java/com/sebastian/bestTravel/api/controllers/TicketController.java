package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.TicketRequest;
import com.sebastian.bestTravel.api.models.response.TicketResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
//@Controller // Se usa cuando se tienen las vistas integradas con un JSP parecido Thymeleaf
public class TicketController {
    public final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@Valid @RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.create(ticketRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.read(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<TicketResponse> put(@Valid @RequestBody TicketRequest request, @PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.update(request, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long flyId) {
        return ResponseEntity.ok(Collections.singletonMap("flyPrice", ticketService.findPrice(flyId)));
    }
}