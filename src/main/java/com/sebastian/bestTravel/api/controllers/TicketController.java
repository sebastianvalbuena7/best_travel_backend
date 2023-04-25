package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.request.TicketRequest;
import com.sebastian.bestTravel.api.models.response.TicketResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
//@Controller // Se usa cuando se tienen las vistas integradas con un JSP como Thymeleaf
public class TicketController {
    public final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.create(ticketRequest));
    }
}