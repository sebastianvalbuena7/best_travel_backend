package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.api.models.response.FlyResponse;
import com.sebastian.bestTravel.domain.entities.FlyEntity;
import com.sebastian.bestTravel.domain.repositories.FlyRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.IFlyService;
import com.sebastian.bestTravel.util.constants.CacheConstants;
import com.sebastian.bestTravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class FlyService implements IFlyService {
    private final FlyRepository flyRepository;
    private final WebClient webClient;

    public FlyService(FlyRepository flyRepository, @Qualifier(value = "currency") WebClient webClient) { // Con @Qualifier le puedo decir cual bean quiero inyectar dado su nombre
        this.flyRepository = flyRepository;
        this.webClient = webClient;
    }

    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return flyRepository.findAll(pageRequest).map(flyEntity -> entityToResponse(flyEntity));
    }

    @Override
    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return flyRepository.selectLessPrice(price).stream().map(flyEntity -> entityToResponse(flyEntity)).collect(Collectors.toSet());
    }


    @Override
    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return flyRepository.selectBetweenPrice(min, max).stream().map(flyEntity -> entityToResponse(flyEntity)).collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return flyRepository.selectOriginDestiny(origin, destiny).stream().map(flyEntity -> entityToResponse(flyEntity)).collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(FlyEntity flyEntity) {
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(flyEntity, flyResponse);
        return flyResponse;
    }
}