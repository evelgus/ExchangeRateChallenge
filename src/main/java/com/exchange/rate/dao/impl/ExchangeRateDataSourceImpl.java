package com.exchange.rate.dao.impl;

import com.exchange.rate.dao.DataSource;
import com.exchange.rate.dto.RateValuesDTO;
import com.exchange.rate.exceptions.ExchangeException;
import com.exchange.rate.exceptions.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@ConditionalOnProperty(
        value = "real_data_source.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class ExchangeRateDataSourceImpl implements DataSource {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateDataSourceImpl.class);

    @Value("${exchange.url}")
    private String url;
    @Value("${access_key}")
    private String accessKey;

    private final WebClient  client;

    public ExchangeRateDataSourceImpl(@Autowired WebClient client) {
        this.client = client;
    }


    @Override
    @Cacheable("rates")
    public RateValuesDTO retrieveRatesForCurrency(String currency) {
        LOG.info("Retrieving rate for currency {}", currency);
        return client.mutate()
                .baseUrl(url)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/live")
                        .queryParam("access_key", accessKey)
                        .queryParam("source", currency)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RateValuesDTO>() {
                })
                .<RateValuesDTO>handle((rate, sink) -> {
                    if (!rate.getSuccess()) {
                        ResponseError error = rate.getResponseError();
                        sink.error(new ExchangeException(error.getCode(), error.getType(), error.getInfo()));
                        return;
                    }
                    sink.next(rate);
                })
                .block();
    }

    @CacheEvict(value = "rates", allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.ratesTTL}")
    public void evictCache() {
        LOG.info("Evicting rates cache");
    }
}
