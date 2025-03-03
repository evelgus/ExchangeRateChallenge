package com.exchange.rate.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExchangeException extends RuntimeException {
    private Integer code;
    private String type;

    public ExchangeException(String message) {
        super(message);
    }
    public ExchangeException(Integer code, String type, String message) {
        super(message);
        this.type = type;
        this.code = code;
    }


}
