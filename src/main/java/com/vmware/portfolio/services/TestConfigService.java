package com.vmware.portfolio.services;

import com.vmware.portfolio.demo.LoadTestScheduler;
import com.vmware.portfolio.models.TestConfig;
import com.vmware.portfolio.repositories.TestConfigRepository;
import com.vmware.portfolio.utils.LoadTester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TestConfigService {

    @Autowired
    TestConfigRepository confRepository;

    public List<TestConfig> getConfigurations () {
        return confRepository.findAll();
    }

    public TestConfig getConfigByName (String name) {
        return confRepository.findByName(name);
    }


    public void stopAllTest () {
        saveConfigByName("STATUS", "STOPPED");
        saveConfigByName("MEMORY_TEST", "STOPPED");
        saveConfigByName("MEMORY_TEST_TARGET", null);
        saveConfigByName("CPU_TEST", "STOPPED");

        LoadTester.getInstance().stopAll();
    }

    public void startMemoryTest (long target) throws InterruptedException {
        String newTestId = System.currentTimeMillis()+"";
        saveConfigByName("MEMORY_TEST_ID", newTestId);
        saveConfigByName("MEMORY_TEST", "RUNNING");
        saveConfigByName("MEMORY_TEST_TARGET", target+"");
        startNewMemoryTest(getConfigByName("MEMORY_TEST_ID"), getConfigByName("MEMORY_TEST_TARGET"));
    }

    public void startCpuTest () throws InterruptedException {
        String newTestId = System.currentTimeMillis()+"";
        saveConfigByName("CPU_TEST_ID", newTestId);
        saveConfigByName("CPU_TEST", "RUNNING");

        startNewCpuTest(getConfigByName("CPU_TEST_ID"));
    }

    public void scheduleMemoryTests () throws InterruptedException {

        TestConfig memoryTest = getConfigByName("MEMORY_TEST");
        TestConfig memoryTestId = getConfigByName("MEMORY_TEST_ID");
        TestConfig memoryTestTarget = getConfigByName("MEMORY_TEST_TARGET");

        // Handle Memory Tests
        // If supposed to be running
        if (memoryTest != null && memoryTest.getValue().equals("RUNNING")) {
            if (!LoadTester.getInstance().isMemoryTestActive()) {
                if (memoryTestTarget != null) {
                    startNewMemoryTest(memoryTestId, memoryTestTarget);
                }
            }
            else {
                //Is active but different name
                if (memoryTestId != null && !memoryTestId.getValue().equals(LoadTester.getInstance().getMemoryTestId())) {
                    LoadTester.getInstance().stopMemoryTest();
                    System.out.println("Stopping existing memory test => "+memoryTestId.getValue());
                    startNewMemoryTest(memoryTestId, memoryTestTarget);
                }
                else {
                    System.out.println("Memory Test Already Running...");
                }
            }
        }
        else {
            if (LoadTester.getInstance().isMemoryTestActive()) {
                System.out.println("Stopping memory Test");
                LoadTester.getInstance().stopMemoryTest();
            }
        }
    }

    public void scheduleCpuTests () throws InterruptedException {

        TestConfig cpuTest = getConfigByName("CPU_TEST");
        TestConfig cpuTestId = getConfigByName("CPU_TEST_ID");

        // Handle Memory Tests
        // If supposed to be running
        if (cpuTest != null && cpuTest.getValue().equals("RUNNING")) {
            if (!LoadTester.getInstance().isCpuTestActive()) {
                System.out.println("From Scheduler...");
               startNewCpuTest(cpuTestId);
            }
            else {
                //Is active but different name
                if (cpuTestId != null && cpuTestId.getValue().equals(LoadTester.getInstance().getCpuTestId())) {
                    LoadTester.getInstance().stopCpuTest();
                    System.out.println("Stopping existing cpu test => "+cpuTestId.getValue());
                    startNewCpuTest(cpuTestId);
                }
            }
        }
        else {
            if (LoadTester.getInstance().isCpuTestActive()) {
                LoadTester.getInstance().stopCpuTest();
            }
        }
    }

    public void startNewMemoryTest (TestConfig testId, TestConfig memoryTestTarget) throws InterruptedException {
        System.out.println("Starting new memory test => "+memoryTestTarget.getValue());
        LoadTester.getInstance().setMemoryTestId(testId.getValue());
        LoadTester.getInstance().launchMemoryTest(Long.parseLong(memoryTestTarget.getValue()));
    }

    public void startNewCpuTest (TestConfig testId) throws InterruptedException {
        System.out.println("Starting new cpu test ");
        LoadTester.getInstance().setCpuTestId(testId.getValue());
        LoadTester.getInstance().launchCPUTest();
    }

    public void saveConfigByName (String name, String value) {
        TestConfig conf = confRepository.findByName(name);
        if (conf != null) {
            conf.setValue(value);
            confRepository.save(conf);
        }
        else {
            conf = new TestConfig();
            conf.setName(name);
            conf.setValue(value);
            confRepository.save(conf);
        }
    }
}
