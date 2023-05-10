package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.api.models.response.FlyResponse;
import com.sebastian.bestTravel.infrastructure.abstract_services.IFlyService;
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
@RequestMapping("/fly")
@AllArgsConstructor
@Tag(name = "Fly")
public class FlyController {
    private final IFlyService flyService;

    @Operation(summary = "Return a page with all flights")
    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAll(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = flyService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list with less price fly")
    @GetMapping("lessPrice")
    public ResponseEntity<Set<FlyResponse>> getLessPrice(@RequestParam BigDecimal price) {
        var response = flyService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list of flights with between price")
    @GetMapping("betweenPrice")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        var response = flyService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list with flights the same origin destiny")
    @GetMapping("originDestiny")
    public ResponseEntity<Set<FlyResponse>> getOriginDestiny(@RequestParam String origin, @RequestParam String destiny) {
        var response = flyService.readByOriginDestiny(origin, destiny);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}