package com.example.analytics.service.impl;

import com.example.analytics.dto.ApiMetricEvent;
import com.example.analytics.entity.ApiUsageLog;
import com.example.analytics.repository.ApiUsageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnalyticsServiceTest {

    @Mock
    private ApiUsageRepository repository;

    @InjectMocks
    private AnalyticsServiceImpl service;

    @Test
    void shouldSaveMetricToDatabase() {

        ApiMetricEvent event = new ApiMetricEvent();
        event.setApiId("api-1");
        event.setEndpoint("/test");
        event.setLatency(200);
        event.setStatusCode(200);
        event.setCost(0.5);

        service.saveMetric(event);

        verify(repository, times(1)).save(any(ApiUsageLog.class));
    }

    @Test
    void shouldMapEventCorrectlyBeforeSaving() {

        ApiMetricEvent event = new ApiMetricEvent();
        event.setEndpoint("/map-test");

        service.saveMetric(event);

        verify(repository, times(1)).save(argThat(log ->
                log.getEndpoint().equals("/map-test")
        ));
    }
}