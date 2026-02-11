package com.sysadmin.monitor.controller;

import com.sysadmin.monitor.dto.SystemMetricDTO;
import com.sysadmin.monitor.entity.SystemMetric;
import com.sysadmin.monitor.service.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MetricController {

    private final MetricService metricService;

    @PostMapping
    public ResponseEntity<SystemMetric> receiveMetric(@RequestBody SystemMetricDTO metricDTO) {
        SystemMetric savedMetric = metricService.saveMetric(metricDTO);
        return new ResponseEntity<>(savedMetric, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SystemMetric>> getLatestMetrics() {
        List<SystemMetric> metrics = metricService.getLatestMetrics();
        return ResponseEntity.ok(metrics);
    }
}