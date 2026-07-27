package com.reshu.apiforge.dto;

public class AdminStatsResponse {

    private long totalUsers;
    private long totalProjects;
    private long totalEndpoints;

    public AdminStatsResponse(
            long totalUsers,
            long totalProjects,
            long totalEndpoints) {

        this.totalUsers = totalUsers;
        this.totalProjects = totalProjects;
        this.totalEndpoints = totalEndpoints;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalProjects() {
        return totalProjects;
    }

    public long getTotalEndpoints() {
        return totalEndpoints;
    }
}