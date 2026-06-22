package com.example.analytics.service.impl;

import com.example.analytics.entity.ApiUsageLog;
import com.example.analytics.repository.ApiUsageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AggregationServiceTest {

    @Mock
    private ApiUsageRepository repository;

    @InjectMocks
    private AggregationServiceImpl service;

    @Test
    void shouldRunHourlyAggregation() {

        when(repository.findLogsAfter(any()))
                .thenReturn(List.of(
                        new ApiUsageLog(),
                        new ApiUsageLog()
                ));

        service.aggregateHourly();

        verify(repository, times(1)).findLogsAfter(any());
    }

    @Test
    void shouldRunDailyAggregation() {

        when(repository.findLogsBetween(any(), any()))
                .thenReturn(List.of(
                        new ApiUsageLog(),
                        new ApiUsageLog(),
                        new ApiUsageLog()
                ));

        service.aggregateDaily();

        verify(repository, times(1)).findLogsBetween(any(), any());
    }

    @Test
    void shouldHandleEmptyLogsGracefully() {

        when(repository.findLogsAfter(any())).thenReturn(List.of());
        when(repository.findLogsBetween(any(), any())).thenReturn(List.of());

        service.aggregateHourly();
        service.aggregateDaily();

        verify(repository, times(1)).findLogsAfter(any());
        verify(repository, times(1)).findLogsBetween(any(), any());
    }
}