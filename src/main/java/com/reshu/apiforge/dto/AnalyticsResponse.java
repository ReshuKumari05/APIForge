package com.reshu.apiforge.dto;

public class AnalyticsResponse {

    private long totalEndpoints;
    private long totalExecutions;
    private long successfulExecutions;
    private long failedExecutions;
    private double successRate;
    private double averageResponseTimeMs;

    public AnalyticsResponse(
            long totalEndpoints,
            long totalExecutions,
            long successfulExecutions,
            long failedExecutions,
            double successRate,
            double averageResponseTimeMs) {

        this.totalEndpoints = totalEndpoints;
        this.totalExecutions = totalExecutions;
        this.successfulExecutions = successfulExecutions;
        this.failedExecutions = failedExecutions;
        this.successRate = successRate;
        this.averageResponseTimeMs = averageResponseTimeMs;
    }

    public long getTotalEndpoints() {
        return totalEndpoints;
    }

    public long getTotalExecutions() {
        return totalExecutions;
    }

    public long getSuccessfulExecutions() {
        return successfulExecutions;
    }

    public long getFailedExecutions() {
        return failedExecutions;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public double getAverageResponseTimeMs() {
        return averageResponseTimeMs;
    }
}