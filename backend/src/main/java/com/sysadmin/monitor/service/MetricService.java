package com.sysadmin.monitor.service;

import com.sysadmin.monitor.dto.SystemMetricDTO;
import com.sysadmin.monitor.entity.SystemMetric;
import com.sysadmin.monitor.repository.SystemMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricService {

    private final SystemMetricRepository metricRepository;

    public SystemMetric saveMetric(SystemMetricDTO dto) {

        SystemMetric metric = SystemMetric.builder()
                .timestamp(LocalDateTime.now())
                .cpuUsage(dto.getCpuUsage())
                .ramUsage(dto.getRamUsage())
                .build();

        SystemMetric savedMetric = metricRepository.save(metric);

        log.info("Metrica guardada - ID: {} CPU: {}% RAM: {}%",
                savedMetric.getId(),
                savedMetric.getCpuUsage(),
                savedMetric.getRamUsage());

        return savedMetric;
    }

    public List<SystemMetric> getLatestMetrics() {
        List<SystemMetric> metrics = metricRepository.findTop20ByOrderByTimestampDesc();
        return metrics.reversed(); // las invertimos para que el grafico vaya de izquierda a derecha
    }
}