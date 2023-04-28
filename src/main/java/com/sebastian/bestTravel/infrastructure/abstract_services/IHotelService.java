package com.sebastian.bestTravel.infrastructure.abstract_services;

import com.sebastian.bestTravel.api.models.response.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse>{
    Set<HotelResponse> readGreaterThan(Integer rating);
}