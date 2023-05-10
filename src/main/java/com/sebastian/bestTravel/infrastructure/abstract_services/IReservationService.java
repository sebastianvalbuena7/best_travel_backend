package com.sebastian.bestTravel.infrastructure.abstract_services;

import com.sebastian.bestTravel.api.models.request.ReservationRequest;
import com.sebastian.bestTravel.api.models.response.ReservationResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{
    BigDecimal findPrice(UUID uuid, Currency currency);
}