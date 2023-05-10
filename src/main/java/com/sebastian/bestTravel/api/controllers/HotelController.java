package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.response.HotelResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.IHotelService;
import com.sebastian.bestTravel.util.enums.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/hotel")
@AllArgsConstructor
@Tag(name = "Hotel")
public class HotelController {
    private final IHotelService hotelService;

    @Operation(summary = "Return a page with all hotels")
    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAll(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = hotelService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list with all hotels of less price")
    @GetMapping("lessPrice")
    public ResponseEntity<Set<HotelResponse>> getLessPrice(@RequestParam BigDecimal price) {
        var response = hotelService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of hoes with between price")
    @GetMapping("betweenPrice")
    public ResponseEntity<Set<HotelResponse>> getBetweenPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        var response = hotelService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list with flights the same rating")
    @GetMapping("rating")
    public ResponseEntity<Set<HotelResponse>> getRating(@RequestParam Integer rating) {
        if (rating > 4) rating = 4;
        if (rating < 1) rating = 1;
        var response = hotelService.readGreaterThan(rating);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}