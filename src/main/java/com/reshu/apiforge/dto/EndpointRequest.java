package com.reshu.apiforge.dto;

import jakarta.validation.constraints.NotBlank;

public class EndpointRequest {

    @NotBlank(message = "HTTP method is required")
    private String method;

    @NotBlank(message = "Endpoint path is required")
    private String path;

    private String description;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}