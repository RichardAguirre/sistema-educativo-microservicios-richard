package com.educativo.asignaturas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private HealthEndpoint healthEndpoint;
    
    @Autowired
    private MetricsEndpoint metricsEndpoint;
    
    @Value("${spring.application.name}")
    private String applicationName;
    
    @Value("${server.port}")
    private String serverPort;

    @GetMapping
    public String dashboard(Model model) {
        HealthComponent health = healthEndpoint.health();
        
        double systemCpuUsage = 0;
        double processCpuUsage = 0;
        long jvmMemoryUsed = 0;
        long jvmMemoryMax = 0;
        
        try {
            systemCpuUsage = metricsEndpoint.metric("system.cpu.usage", null)
                    .getMeasurements().stream()
                    .findFirst()
                    .map(m -> m.getValue() * 100)
                    .orElse(0.0);
            
            processCpuUsage = metricsEndpoint.metric("process.cpu.usage", null)
                    .getMeasurements().stream()
                    .findFirst()
                    .map(m -> m.getValue() * 100)
                    .orElse(0.0);
            
            jvmMemoryUsed = metricsEndpoint.metric("jvm.memory.used", null)
                    .getMeasurements().stream()
                    .findFirst()
                    .map(m -> m.getValue().longValue())
                    .orElse(0L);
            
            jvmMemoryMax = metricsEndpoint.metric("jvm.memory.max", null)
                    .getMeasurements().stream()
                    .findFirst()
                    .map(m -> m.getValue().longValue())
                    .orElse(1L);
        } catch (Exception e) {
        }
        
        double memoryUsagePercent = (double) jvmMemoryUsed / jvmMemoryMax * 100;
        
        model.addAttribute("applicationName", applicationName.toUpperCase());
        model.addAttribute("serverPort", serverPort);
        model.addAttribute("health", health);
        model.addAttribute("systemCpuUsage", String.format("%.2f", systemCpuUsage));
        model.addAttribute("processCpuUsage", String.format("%.2f", processCpuUsage));
        model.addAttribute("memoryUsed", formatSize(jvmMemoryUsed));
        model.addAttribute("memoryMax", formatSize(jvmMemoryMax));
        model.addAttribute("memoryUsagePercent", String.format("%.2f", memoryUsagePercent));
        model.addAttribute("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return "dashboard";
    }
    
    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.2f %sB", bytes / Math.pow(1024, exp), pre);
    }
}