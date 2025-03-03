package com.exchange.rate.dao.impl;

import com.exchange.rate.dao.DataSource;
import com.exchange.rate.dto.RateValuesDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        value="real_data_source.enabled",
        havingValue = "false")
public class DummyDataSource implements DataSource {
    @Override
    public RateValuesDTO retrieveRatesForCurrency(String currency) {
        throw new UnsupportedOperationException("This method added only to show how use different data sources");
    }
}
