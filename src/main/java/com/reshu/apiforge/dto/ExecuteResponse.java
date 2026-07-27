package com.reshu.apiforge.dto;

public class ExecuteResponse {

    private Long executionId;
    private String url;
    private String method;
    private int statusCode;
    private long responseTimeMs;
    private boolean successful;
    private String responseBody;

    public ExecuteResponse(
            Long executionId,
            String url,
            String method,
            int statusCode,
            long responseTimeMs,
            boolean successful,
            String responseBody) {

        this.executionId = executionId;
        this.url = url;
        this.method = method;
        this.statusCode = statusCode;
        this.responseTimeMs = responseTimeMs;
        this.successful = successful;
        this.responseBody = responseBody;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
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

    public String getResponseBody() {
        return responseBody;
    }
}