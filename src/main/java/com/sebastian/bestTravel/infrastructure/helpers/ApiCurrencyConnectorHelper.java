package com.sebastian.bestTravel.infrastructure.helpers;

import com.sebastian.bestTravel.infrastructure.dtos.CurrencyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Currency;

@Component
public class ApiCurrencyConnectorHelper {
    private final WebClient webClient;
    @Value(value = "${api.base-currency}")
    private String baseCurrency;

    public ApiCurrencyConnectorHelper(WebClient webClient) {
        this.webClient = webClient;
    }

    private static final String BASE_CURRENCY_QUERY_PARAM = "?base={base}";
    private static final String SYMBOL_CURRENCY_QUERY_PARAM = "&symbols={symbol}";
    private static final String CURRENCY_PATH = "/fixer/latest";

    public CurrencyDTO getCurrency(Currency currency) {
        return this.webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(CURRENCY_PATH)
                        .query(BASE_CURRENCY_QUERY_PARAM)
                        .query(SYMBOL_CURRENCY_QUERY_PARAM)
                        .build(baseCurrency, currency.getCurrencyCode()))
                .retrieve()
                .bodyToMono(CurrencyDTO.class)
                .block();
    }
}