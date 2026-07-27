package com.reshu.apiforge.dto;

import java.time.LocalDateTime;

public class ExecutionHistoryResponse {

    private Long executionId;
    private Long endpointId;
    private String method;
    private String path;
    private int statusCode;
    private long responseTimeMs;
    private boolean successful;
    private LocalDateTime executedAt;

    public ExecutionHistoryResponse(
            Long executionId,
            Long endpointId,
            String method,
            String path,
            int statusCode,
            long responseTimeMs,
            boolean successful,
            LocalDateTime executedAt) {

        this.executionId = executionId;
        this.endpointId = endpointId;
        this.method = method;
        this.path = path;
        this.statusCode = statusCode;
        this.responseTimeMs = responseTimeMs;
        this.successful = successful;
        this.executedAt = executedAt;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public Long getEndpointId() {
        return endpointId;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getResponseTimeMs() {
        return responseTimeMs;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }
}