package com.exchange.rate.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public interface ExchangeRateService {
    BigDecimal exchangeRate(String fromCurrency, String toCurrency);
    Map<String, BigDecimal> exchangeRatesForCurrency(String currency);
    BigDecimal convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount);
    Map<String, BigDecimal> convertCurrencies(String fromCurrency, Collection<String> toCurrencies, BigDecimal amount);

}
