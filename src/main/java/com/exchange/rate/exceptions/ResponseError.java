package com.exchange.rate.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseError {
    @JsonProperty
    private Integer code;
    @JsonProperty
    private String type;
    @JsonProperty
    private String info;
}
