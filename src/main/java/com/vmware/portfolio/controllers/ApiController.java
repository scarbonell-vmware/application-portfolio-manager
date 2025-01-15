package com.vmware.portfolio.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${portfolio.application.manager.version}")
    private String version;

    @GetMapping("version")
    public Map<String,Object> getOrchestratorVersion() {
        Map<String,Object> versionInfo = new HashMap<>();
        versionInfo.put("name", version);
        return versionInfo;
    }
}
