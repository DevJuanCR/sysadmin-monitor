package com.sysadmin.monitor.repository;

import com.sysadmin.monitor.entity.SystemMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMetricRepository extends JpaRepository<SystemMetric, Long> {
}