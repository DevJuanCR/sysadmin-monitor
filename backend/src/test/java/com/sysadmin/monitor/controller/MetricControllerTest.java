package com.sysadmin.monitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysadmin.monitor.dto.SystemMetricDTO;
import com.sysadmin.monitor.entity.SystemMetric;
import com.sysadmin.monitor.service.MetricService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MetricController.class)
class MetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MetricService metricService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postMetric_withValidData_shouldReturn201() throws Exception {
        SystemMetricDTO dto = new SystemMetricDTO(45.2, 67.8);

        SystemMetric saved = SystemMetric.builder()
                .id(1L)
                .timestamp(LocalDateTime.now())
                .cpuUsage(45.2)
                .ramUsage(67.8)
                .build();

        when(metricService.saveMetric(any(SystemMetricDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cpuUsage").value(45.2))
                .andExpect(jsonPath("$.ramUsage").value(67.8));
    }

    @Test
    void postMetric_withNullCpu_shouldReturn400() throws Exception {
        String json = "{\"ramUsage\": 67.8}";

        mockMvc.perform(post("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.cpuUsage").exists());
    }

    @Test
    void postMetric_withCpuOver100_shouldReturn400() throws Exception {
        String json = "{\"cpuUsage\": 150.0, \"ramUsage\": 67.8}";

        mockMvc.perform(post("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.cpuUsage").exists());
    }

    @Test
    void postMetric_withNegativeRam_shouldReturn400() throws Exception {
        String json = "{\"cpuUsage\": 45.2, \"ramUsage\": -5.0}";

        mockMvc.perform(post("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.ramUsage").exists());
    }

    @Test
    void getMetrics_shouldReturn200() throws Exception {
        SystemMetric metric = SystemMetric.builder()
                .id(1L)
                .timestamp(LocalDateTime.now())
                .cpuUsage(45.2)
                .ramUsage(67.8)
                .build();

        when(metricService.getLatestMetrics()).thenReturn(List.of(metric));

        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cpuUsage").value(45.2));
    }

    @Test
    void getMetrics_withNoData_shouldReturnEmptyList() throws Exception {
        when(metricService.getLatestMetrics()).thenReturn(List.of());

        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}