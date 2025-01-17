package com.vmware.portfolio.controllers;

import com.vmware.portfolio.models.TestConfig;
import com.vmware.portfolio.services.TestConfigService;
import com.vmware.portfolio.utils.LoadTester;
import com.vmware.portfolio.utils.PropUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ContextController {

    @Autowired
    PropUtils propUtils;

    @Autowired
    TestConfigService testConfigService;

    @Value("${portfolio.application.manager.version}")
    private String version;

    @GetMapping("version")
    public Map<String,Object> getOrchestratorVersion() {
        Map<String,Object> versionInfo = new HashMap<>();
        versionInfo.put("name", version);
        return versionInfo;
    }

    @GetMapping(value = "envProps",produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> getEnvironmentProps() throws JSONException {
        return propUtils.getEnvironmentProps();
    }

    @GetMapping(value = "appProps",produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> getAppProperties() throws JSONException {
        return propUtils.getAppProperties();
    }

    @GetMapping(value = "activeProfiles",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getActiveProfile () throws JSONException {
        return propUtils.getActiveProfile();
    }

    @GetMapping(value = "memoryUsed")
    public String getMemory () {
        return LoadTester.getInstance().getUsedMemory()+"M";
    }

    @GetMapping(value = "memoryAllocated")
    public String getMaxMemory () {
        return LoadTester.getInstance().getAvailableMemory()+"M";
    }

    @GetMapping(value = "cpuUsage")
    public String getCpu () {
        return LoadTester.getInstance().getCPUUsage()+"";
    }

    @PostMapping(value = "increaseMemory")
    public ResponseEntity increaseMemory(@RequestBody String target) throws InterruptedException {
        testConfigService.startMemoryTest(Long.parseLong(target));
        return new ResponseEntity<String>("Started...",HttpStatus.OK);
    }

    @PostMapping("increaseCPU")
    public ResponseEntity increaseCPU() throws InterruptedException {
        testConfigService.startCpuTest();
        return new ResponseEntity<String>("Started...",HttpStatus.OK);
    }

    @PostMapping("stopLoadTests")
    public String stopLoadTest() throws InterruptedException {
        testConfigService.stopAllTest();
        return "Stopped...";
    }
}
