package com.exchange.rate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class RateValuesDTO extends ExchangeRateDTO {
    @JsonProperty("quotes")
    private Map<String, BigDecimal> rates;
}
