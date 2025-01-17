package com.vmware.portfolio.demo;

import com.vmware.portfolio.services.TestConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoadTestScheduler {

    @Autowired
    TestConfigService testConfigService;

    private static final Logger log = LoggerFactory.getLogger(LoadTestScheduler.class);

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws InterruptedException {
        testConfigService.scheduleMemoryTests();
        testConfigService.scheduleCpuTests();
    }
}
