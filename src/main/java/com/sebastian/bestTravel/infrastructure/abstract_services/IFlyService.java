package com.sebastian.bestTravel.infrastructure.abstract_services;

import com.sebastian.bestTravel.api.models.response.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse>{
    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}