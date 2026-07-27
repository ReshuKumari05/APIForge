package com.reshu.apiforge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_executions")
public class ApiExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endpoint_id", nullable = false)
    private ApiEndpoint endpoint;

    @Column(nullable = false)
    private Integer statusCode;

    @Column(nullable = false)
    private Long responseTimeMs;

    @Column(nullable = false)
    private Boolean successful;

    @Column(nullable = false)
    private LocalDateTime executedAt;

    public ApiExecution() {
    }

    public Long getId() {
        return id;
    }

    public ApiEndpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(ApiEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}