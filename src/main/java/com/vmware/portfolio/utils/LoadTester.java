package com.vmware.portfolio.utils;

import java.util.ArrayList;
import java.util.List;

public class LoadTester {


        public static void increaseMemory() throws InterruptedException {
            List<Object> memoryLeakList = new ArrayList<>();
            long index = 0;
            while (index < 1100) {
                // Create large objects to consume memory
                byte[] data = new byte[1024 * 1024]; // 1MB

                // Add the object to the list to prevent garbage collection
                memoryLeakList.add(data);

                // Optional: Sleep to slow down memory consumption
                Thread.sleep(1000);
                index++;
                System.out.println("Index: "+index);
            }
        }

        public static void increaseCPU () {
            int numThreads = Runtime.getRuntime().availableProcessors(); // Use all available cores

            for (int i = 0; i < numThreads; i++) {
                new Thread(() -> {
                    while (true) {
                        performHeavyTask();
                    }
                }).start();
            }
        }

    private static void performHeavyTask() {
        // Perform some CPU-intensive operation
        long result = 0;
        for (long i = 0; i < 100000000L; i++) {
            result += i;
        }
    }
}
