package com.vmware.portfolio.utils;

import lombok.Getter;
import lombok.Setter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LoadTester {

    private boolean active = false;
    private List<Thread> threads = new ArrayList<>();
    private static final class InstanceHolder {
        private static final LoadTester INSTANCE = new LoadTester();
    }

    public void stop () {
        killAllThreads ();
        setActive(false);
    }

    public void start () {
        setActive(true);
    }

    public static LoadTester getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void launchMemoryTest (long target) throws InterruptedException {
        if (isActive()) {
            LoadTester.getInstance().stop();
            Thread.sleep(2000);
        }
        start();

        Thread thread = new Thread(() -> {
            try {
                increaseMemory(target);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void launchCPUTest () throws InterruptedException {
        if (isActive()) {
            LoadTester.getInstance().stop();
            Thread.sleep(2000);
        }
        start();

        Thread thread = new Thread(() -> {
            increaseCPU();
        });
        thread.start();
    }
        public void increaseMemory(long target) throws InterruptedException {
            List<Object> memoryLeakList = new ArrayList<>();

            while (true && active) {

                if (getUsedMemory () < target) {
                    System.out.println(getUsedMemory()+"M / "+target+"M");
                    // Create large objects to consume memory
                    byte[] data = new byte[1024 * 1024]; // 1MB

                    // Add the object to the list to prevent garbage collection
                    memoryLeakList.add(data);
                }
                else {
                    System.out.println("Target Memory Reached..."+getUsedMemory());
                }
                // Optional: Sleep to slow down memory consumption
                Thread.sleep(500);

            }
            System.out.println("Releasing memory...");
            memoryLeakList = new ArrayList<>();
        }

        public double getUsedMemory () {
            Runtime runtime = Runtime.getRuntime();

            // Get total memory available to the JVM
            long totalMemory = runtime.totalMemory();

            // Get free memory available to the JVM
            long freeMemory = runtime.freeMemory();

            // Calculate used memory
            long usedMemory = totalMemory - freeMemory;

            double megabytes = usedMemory / (1024.0 * 1024.0);

            return Math.round(megabytes * 100.0) / 100.0;
        }

        public double getCPUUsage () {
         OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

            // Get the CPU time used by the process in nanoseconds
            double processCpuTime = osBean.getSystemLoadAverage();
            return Math.round(processCpuTime * 100.0) / 100.0;
        }

        public void increaseCPU () {

            int numThreads = Runtime.getRuntime().availableProcessors(); // Use all available cores
            System.out.println("# Processor Available: "+numThreads);
            for (int i = 0; i < numThreads; i++) {
                System.out.println("# "+i+" started...");

                Thread t = new Thread(() -> {
                    while (true && active) {
                        performHeavyTask();
                    }
                });

                t.start();
                threads.add(t);
            }
        }

        public void killAllThreads () {
           for (Thread t: threads) {
               t.interrupt();
               System.out.println("Interrupt Thread "+t.getName());
           }
        }

    private void performHeavyTask() {
        // Perform some CPU-intensive operation
        long result = 0;
        for (long i = 0; i < 100000000L; i++) {
            result += i;
            if (!isActive()) {
                System.out.println("Releasing Thread...");
                break;
            }
        }
    }
}
