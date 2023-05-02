package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.api.models.response.FlyResponse;
import com.sebastian.bestTravel.domain.entities.FlyEntity;
import com.sebastian.bestTravel.domain.repositories.FlyRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.IFlyService;
import com.sebastian.bestTravel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {
    private final FlyRepository flyRepository;

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
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return flyRepository.selectLessPrice(price).stream().map(flyEntity -> entityToResponse(flyEntity)).collect(Collectors.toSet());
    }


    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return flyRepository.selectBetweenPrice(min, max).stream().map(flyEntity -> entityToResponse(flyEntity)).collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return flyRepository.selectOriginDestiny(origin, destiny).stream().map(flyEntity -> entityToResponse(flyEntity)).collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(FlyEntity flyEntity) {
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(flyEntity, flyResponse);
        return flyResponse;
    }
}