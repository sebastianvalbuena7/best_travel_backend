package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.TicketRequest;
import com.sebastian.bestTravel.api.models.response.TicketResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
//@Controller // Se usa cuando se tienen las vistas integradas con un JSP parecido Thymeleaf
public class TicketController {
    public final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.create(ticketRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.read(id));
    }
}