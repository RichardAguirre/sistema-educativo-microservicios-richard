package com.educativo.monitor.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class DashboardController {

    @Autowired
    private DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String dashboard(Model model) {
        List<String> serviceIds = discoveryClient.getServices();
        Map<String, Object> servicesInfo = new HashMap<>();

        if (serviceIds.isEmpty()) {
            addManualService(servicesInfo, "usuarios-servicio", "http://localhost:8081");
            addManualService(servicesInfo, "asignaturas-servicio", "http://localhost:8082");
            addManualService(servicesInfo, "matriculas-servicio", "http://localhost:8083");
        } else {
            for (String serviceId : serviceIds) {
                if (!serviceId.equals("monitor-service")) {
                    List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
                    List<Map<String, Object>> instancesData = new ArrayList<>();

                    for (ServiceInstance instance : instances) {
                        Map<String, Object> instanceInfo = new HashMap<>();
                        String healthEndpoint = instance.getUri() + "/actuator/health";
                        String metricsEndpoint = instance.getUri() + "/actuator/metrics";

                        try {
                            Map<?, ?> healthData = restTemplate.getForObject(healthEndpoint, Map.class);
                            instanceInfo.put("health", healthData);

                            Map<?, ?> cpuMetrics = restTemplate.getForObject(metricsEndpoint + "/system.cpu.usage", Map.class);
                            Map<?, ?> memoryMetrics = restTemplate.getForObject(metricsEndpoint + "/jvm.memory.used", Map.class);

                            instanceInfo.put("cpu", cpuMetrics);
                            instanceInfo.put("memory", memoryMetrics);
                            instanceInfo.put("uri", instance.getUri().toString());
                            instanceInfo.put("serviceId", serviceId);

                            instancesData.add(instanceInfo);
                        } catch (Exception e) {
                            instanceInfo.put("error", "Error obteniendo información: " + e.getMessage());
                            instanceInfo.put("uri", instance.getUri().toString());
                            instanceInfo.put("serviceId", serviceId);
                            instancesData.add(instanceInfo);
                        }
                    }

                    servicesInfo.put(serviceId, instancesData);
                }
            }
        }

        model.addAttribute("servicesInfo", servicesInfo);
        model.addAttribute("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        model.addAttribute("grafanaUrl", "http://localhost:3000");
        model.addAttribute("prometheusUrl", "http://localhost:9090");

        return "dashboard";
    }

    private void addManualService(Map<String, Object> servicesInfo, String serviceName, String serviceUrl) {
        List<Map<String, Object>> instancesData = new ArrayList<>();
        Map<String, Object> instanceInfo = new HashMap<>();
        String healthEndpoint = serviceUrl + "/actuator/health";
        String metricsEndpoint = serviceUrl + "/actuator/metrics";

        try {
            Map<?, ?> healthData = restTemplate.getForObject(healthEndpoint, Map.class);
            instanceInfo.put("health", healthData);

            Map<?, ?> cpuMetrics = restTemplate.getForObject(metricsEndpoint + "/system.cpu.usage", Map.class);
            Map<?, ?> memoryMetrics = restTemplate.getForObject(metricsEndpoint + "/jvm.memory.used", Map.class);

            instanceInfo.put("cpu", cpuMetrics);
            instanceInfo.put("memory", memoryMetrics);
            instanceInfo.put("uri", serviceUrl);
            instanceInfo.put("serviceId", serviceName);

            instancesData.add(instanceInfo);
        } catch (Exception e) {
            instanceInfo.put("error", "Error obteniendo información: " + e.getMessage());
            instanceInfo.put("uri", serviceUrl);
            instanceInfo.put("serviceId", serviceName);
            instancesData.add(instanceInfo);
        }

        servicesInfo.put(serviceName, instancesData);
    }
}
