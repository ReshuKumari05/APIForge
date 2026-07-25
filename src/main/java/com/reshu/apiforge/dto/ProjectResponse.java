package com.reshu.apiforge.dto;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String baseUrl;

    public ProjectResponse(
            Long id,
            String name,
            String description,
            String baseUrl) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.baseUrl = baseUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}