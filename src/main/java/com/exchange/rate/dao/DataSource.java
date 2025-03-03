package com.exchange.rate.dao;

import com.exchange.rate.dto.RateValuesDTO;

public interface DataSource {
    RateValuesDTO retrieveRatesForCurrency(String currency);
}
