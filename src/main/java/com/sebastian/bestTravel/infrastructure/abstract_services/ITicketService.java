package com.sebastian.bestTravel.infrastructure.abstract_services;

import com.sebastian.bestTravel.api.models.request.TicketRequest;
import com.sebastian.bestTravel.api.models.response.TicketResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{
    BigDecimal findPrice(Long flyId, Currency currency);
}