package com.exchange.rate.dto;


import com.exchange.rate.exceptions.ResponseError;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class ExchangeRateDTO {
    @JsonProperty
    private Boolean success;
    @JsonProperty
    private String terms;
    @JsonProperty
    private String privacy;
    @JsonProperty
    private Integer timestamp;
    @JsonProperty("error")
    private ResponseError responseError;
}
