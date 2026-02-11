package com.sysadmin.monitor.service;

import com.sysadmin.monitor.dto.SystemMetricDTO;
import com.sysadmin.monitor.entity.SystemMetric;
import com.sysadmin.monitor.repository.SystemMetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    @Mock
    private SystemMetricRepository metricRepository;

    @InjectMocks
    private MetricService metricService;

    @Test
    void saveMetric_shouldSaveAndReturnMetric() {
        SystemMetricDTO dto = new SystemMetricDTO(45.2, 67.8);

        SystemMetric saved = SystemMetric.builder()
                .id(1L)
                .timestamp(LocalDateTime.now())
                .cpuUsage(45.2)
                .ramUsage(67.8)
                .build();

        when(metricRepository.save(any(SystemMetric.class))).thenReturn(saved);

        SystemMetric result = metricService.saveMetric(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(45.2, result.getCpuUsage());
        assertEquals(67.8, result.getRamUsage());
        verify(metricRepository, times(1)).save(any(SystemMetric.class));
    }

    @Test
    void getLatestMetrics_shouldReturnMetricsInOrder() {
        SystemMetric m1 = SystemMetric.builder()
                .id(2L)
                .timestamp(LocalDateTime.now().minusSeconds(10))
                .cpuUsage(30.0)
                .ramUsage(50.0)
                .build();

        SystemMetric m2 = SystemMetric.builder()
                .id(1L)
                .timestamp(LocalDateTime.now().minusSeconds(20))
                .cpuUsage(20.0)
                .ramUsage(40.0)
                .build();

        // el repository devuelve desc (mas reciente primero)
        when(metricRepository.findTop20ByOrderByTimestampDesc()).thenReturn(List.of(m1, m2));

        List<SystemMetric> result = metricService.getLatestMetrics();

        assertEquals(2, result.size());
        // el service las invierte para el grafico (mas antigua primero)
        assertEquals(20.0, result.get(0).getCpuUsage());
        assertEquals(30.0, result.get(1).getCpuUsage());
    }

    @Test
    void getLatestMetrics_shouldReturnEmptyList() {
        when(metricRepository.findTop20ByOrderByTimestampDesc()).thenReturn(List.of());

        List<SystemMetric> result = metricService.getLatestMetrics();

        assertTrue(result.isEmpty());
    }
}