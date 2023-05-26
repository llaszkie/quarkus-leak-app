package com.capgemini;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.lang.management.*;

@Path("/")
public class MyLeak {

    @Inject
    LeakMemoryService leakMemoryService;

    @Inject
    LeakCpuService leakCpuService;

    @GET
    public String leakResources(
        @QueryParam("leak-mem") boolean leakMemory,
        @QueryParam("leak-cpu") boolean leakCpu) {

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Resources leaked!");

        if (leakMemory) {
            Thread memoryThread = new Thread(() -> leakMemoryService.leakMemory());
            memoryThread.start();
        }

        if (leakCpu) {
            Thread cpuThread = new Thread(() -> leakCpuService.leakCpu());
            cpuThread.start();
        }

        // Get current JVM memory and CPU consumption
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        responseBuilder.append("\n\nJVM Memory Usage:");
        appendPrettyMemoryUsage(responseBuilder, "Heap Memory Usage", memoryBean.getHeapMemoryUsage());
        appendPrettyMemoryUsage(responseBuilder, "Non-Heap Memory Usage", memoryBean.getNonHeapMemoryUsage());

        responseBuilder.append("\n\nJVM Threads:");
        responseBuilder.append("\nThread Count: ").append(threadBean.getThreadCount());
        responseBuilder.append("\nPeak Thread Count: ").append(threadBean.getPeakThreadCount());

        responseBuilder.append("\n\nCPU Usage:");
        responseBuilder.append("\nSystem Load Average: ").append(osBean.getSystemLoadAverage());
        responseBuilder.append("\nJVM CPU Usage: ").append(getJvmCpuUsage()).append("%");

        return responseBuilder.toString();
    }

    private void appendPrettyMemoryUsage(StringBuilder builder, String title, MemoryUsage memoryUsage) {
        builder.append("\n").append(title);
        builder.append("\n  Initial: ").append(toMiB(memoryUsage.getInit()));
        builder.append("\n  Used: ").append(toMiB(memoryUsage.getUsed()));
        builder.append("\n  Committed: ").append(toMiB(memoryUsage.getCommitted()));
        builder.append("\n  Max: ").append(toMiB(memoryUsage.getMax()));
    }

    private long toMiB(long bytes) {
        return bytes / (1024 * 1024);
    }

    private double getJvmCpuUsage() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long totalCpuTime = 0;
        for (long threadId : threadBean.getAllThreadIds()) {
            totalCpuTime += threadBean.getThreadCpuTime(threadId);
        }

        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        double cpuUsage = (double) totalCpuTime / (uptime * 1000000); // Convert to seconds
        return cpuUsage * 100;
    }
}
