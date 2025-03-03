package com.exchange.rate.controllers;

import com.exchange.rate.services.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController("/exchange")
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeController(@Autowired final ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Operation(description = "Method returns current exchange rate between two currencies")
    @GetMapping("/rate")
    public BigDecimal exchangeRate(@RequestParam String fromCurrency, @RequestParam String toCurrency) {
        return exchangeRateService.exchangeRate(fromCurrency, toCurrency);
    }

    @Operation(description = "Method returns current exchange rates for one currency to all available currencies")
    @GetMapping("/rates")
    public Map<String, BigDecimal> exchangeRatesForCurrency(@RequestParam String currency) {
        return exchangeRateService.exchangeRatesForCurrency(currency);
    }

    @Operation(description = "Method convert given amount of one currency to another")
    @GetMapping("/convert")
    public BigDecimal convertCurrency(@RequestParam String fromCurrency, @RequestParam String toCurrency, BigDecimal amount) {
        return exchangeRateService.convertCurrency(fromCurrency, toCurrency, amount);
    }

    @Operation(description = "Method convert given amount of one currency to list of another currencies")
    @GetMapping("/convert_to_currencies")
    public Map<String, BigDecimal> convertCurrency(@RequestParam String fromCurrency, @RequestParam List<String> toCurrencies, BigDecimal amount) {
        return exchangeRateService.convertCurrencies(fromCurrency, toCurrencies, amount);
    }
}
