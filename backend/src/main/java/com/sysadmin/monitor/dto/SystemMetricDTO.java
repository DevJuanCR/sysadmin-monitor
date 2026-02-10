package com.sysadmin.monitor.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemMetricDTO {

    private Double cpuUsage;
    private Double ramUsage;
}