package com.reshu.apiforge.dto;

public class AdminProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String baseUrl;
    private Long ownerId;
    private String ownerEmail;

    public AdminProjectResponse(
            Long id,
            String name,
            String description,
            String baseUrl,
            Long ownerId,
            String ownerEmail) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.baseUrl = baseUrl;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }
}