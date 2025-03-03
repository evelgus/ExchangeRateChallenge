package com.exchange.rate.services.impl;

import com.exchange.rate.dao.DataSource;
import com.exchange.rate.dto.RateValuesDTO;
import com.exchange.rate.exceptions.ExchangeException;
import com.exchange.rate.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final DataSource dataSource;

    public ExchangeRateServiceImpl(@Autowired final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public BigDecimal exchangeRate(String fromCurrency, String toCurrency) {
        RateValuesDTO rate = dataSource.retrieveRatesForCurrency(fromCurrency);

        return Optional.ofNullable(rate.getRates().get(fromCurrency.toUpperCase() + toCurrency.toUpperCase()))
                .orElseThrow(() ->
                        new ExchangeException("Could not retrieve rate for convert from  " + fromCurrency + " to " + toCurrency)
                );
    }

    @Override
    public Map<String, BigDecimal> exchangeRatesForCurrency(String currency) {
        return dataSource.retrieveRatesForCurrency(currency).getRates();
    }

    @Override
    public BigDecimal convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
        BigDecimal rate = exchangeRatesForCurrency(fromCurrency).get(fromCurrency.toUpperCase() + toCurrency.toUpperCase());
        return rate.multiply(amount);
    }

    @Override
    public Map<String, BigDecimal> convertCurrencies(String fromCurrency, Collection<String> toCurrencies, BigDecimal amount) {
        var from = fromCurrency.toUpperCase();
        Map<String, BigDecimal> rates = exchangeRatesForCurrency(fromCurrency);
        return toCurrencies
                .stream()
                .map(currency -> {
                    currency = currency.toUpperCase();
                    if (rates.containsKey(from + currency)) {
                        return Map.of(from + currency, amount.multiply(rates.get(from + currency)));
                    } else {
                        return Map.of("Impossible to convert " + from + " to " + currency, BigDecimal.ZERO);
                    }
                })
                .flatMap(map -> map.entrySet()
                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
