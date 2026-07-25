package com.reshu.apiforge.dto;

public class EndpointResponse {

    private Long id;
    private String method;
    private String path;
    private String description;
    private Long projectId;

    public EndpointResponse(
            Long id,
            String method,
            String path,
            String description,
            Long projectId) {

        this.id = id;
        this.method = method;
        this.path = path;
        this.description = description;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public Long getProjectId() {
        return projectId;
    }
}