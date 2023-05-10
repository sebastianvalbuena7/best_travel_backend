package com.sebastian.bestTravel.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Data
public class CurrencyDTO {
    @JsonProperty(value = "date")
    private LocalDate exchangeDate;

    @JsonProperty(value = "rates")
    private Map<Currency, BigDecimal> rates;
}