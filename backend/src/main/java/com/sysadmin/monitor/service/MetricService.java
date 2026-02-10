package com.sysadmin.monitor.service;

import com.sysadmin.monitor.dto.SystemMetricDTO;
import com.sysadmin.monitor.entity.SystemMetric;
import com.sysadmin.monitor.repository.SystemMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricService {

    private final SystemMetricRepository metricRepository;

    public SystemMetric saveMetric(SystemMetricDTO dto) {

        SystemMetric metric = SystemMetric.builder()
                .timestamp(LocalDateTime.now()) // lo ponemos en el servidor no confiamos en el cliente
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
}