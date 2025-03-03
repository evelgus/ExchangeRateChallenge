package com.exchange.rate.services.impl;

import com.exchange.rate.dao.DataSource;
import com.exchange.rate.dto.RateValuesDTO;
import com.exchange.rate.exceptions.ExchangeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceImplTest {
    @Mock
    private DataSource dataSource;
    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService ;

    @Before
    public void setUp() {
        RateValuesDTO rateValuesDTO = new RateValuesDTO();
        rateValuesDTO.setSuccess(true);
        Map<String, BigDecimal> rates = Map.of("USDUER", BigDecimal.valueOf(1.2), "USDGBP", BigDecimal.valueOf(1.3));
        rateValuesDTO.setRates(rates);
        when(dataSource.retrieveRatesForCurrency(anyString())).thenReturn(rateValuesDTO);
    }

    @Test
    public void exchangeRateTest() {

        BigDecimal result = exchangeRateService.exchangeRate("USD", "EUR");
        assertEquals(BigDecimal.valueOf(1.3), result);
    }

    @Test(expected = ExchangeException.class)
    public void exchangeRateExceptionTest() {
        exchangeRateService.exchangeRate("TEST", "TEST1");
    }

    @Test
    public void exchangeRatesForCurrency() {
        Map<String, BigDecimal> result = exchangeRateService.exchangeRatesForCurrency("USD");
        assertTrue(result.containsKey("USDUER"));
        assertTrue(result.containsKey("USDGBP"));
    }

    @Test
    public void convertCurrencyTest() {
        BigDecimal result = exchangeRateService.convertCurrency("USD", "GBP", BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1300.0), result);
    }

    @Test
    public void convertCurrencies() {
        Map<String, BigDecimal> result= exchangeRateService.convertCurrencies("USD", List.of("GBP", "NonExistingCurrency"), BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1300.0), result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("Impossible to convert USD to NONEXISTINGCURRENCY"));
    }


}