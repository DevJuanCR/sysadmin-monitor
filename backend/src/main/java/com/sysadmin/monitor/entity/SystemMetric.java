package com.sysadmin.monitor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // clave primaria la genera la BD automaticamente

    @Column(nullable = false)
    private LocalDateTime timestamp; // momento exacto en que se tomo la lectura

    @Column(nullable = false)
    private Double cpuUsage; // porcentaje de uso de CPU de 0.0 a 100.0

    @Column(nullable = false)
    private Double ramUsage; // porcentaje de uso de RAM de 0.0 a 100.0
}