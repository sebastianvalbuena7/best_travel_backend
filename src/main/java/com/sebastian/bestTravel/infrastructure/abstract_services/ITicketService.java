package com.sebastian.bestTravel.infrastructure.abstract_services;

import com.sebastian.bestTravel.api.models.request.TicketRequest;
import com.sebastian.bestTravel.api.models.response.TicketResponse;

import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{
}