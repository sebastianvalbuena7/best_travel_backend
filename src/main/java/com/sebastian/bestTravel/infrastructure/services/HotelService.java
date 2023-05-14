package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.api.models.response.HotelResponse;
import com.sebastian.bestTravel.domain.entities.HotelEntity;
import com.sebastian.bestTravel.domain.repositories.HotelRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.IHotelService;
import com.sebastian.bestTravel.util.constants.CacheConstants;
import com.sebastian.bestTravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class HotelService implements IHotelService {
    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return hotelRepository.findAll(pageRequest).map(hotel -> entityToResponse(hotel));
    }

    @Override
    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return hotelRepository.findByPriceLessThan(price).stream().map(hotel -> entityToResponse(hotel)).collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return hotelRepository.findByPriceBetween(min, max).stream().map(hotel -> entityToResponse(hotel)).collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readGreaterThan(Integer rating) {
        return hotelRepository.findByRatingGreaterThan(rating).stream().map(hotel -> entityToResponse(hotel)).collect(Collectors.toSet());
    }

    private HotelResponse entityToResponse(HotelEntity hotel) {
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(hotel, hotelResponse);
        return  hotelResponse;
    }
}
