package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.AdminProjectResponse;
import com.reshu.apiforge.dto.AdminStatsResponse;
import com.reshu.apiforge.dto.AdminUserResponse;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.entity.User;
import com.reshu.apiforge.repository.ApiEndpointRepository;
import com.reshu.apiforge.repository.ProjectRepository;
import com.reshu.apiforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ApiEndpointRepository apiEndpointRepository;

    public AdminService(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            ApiEndpointRepository apiEndpointRepository) {

        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.apiEndpointRepository = apiEndpointRepository;
    }

    public List<AdminUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapUser)
                .toList();
    }

    public List<AdminProjectResponse> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapProject)
                .toList();
    }

    public AdminStatsResponse getStats() {

        long totalUsers = userRepository.count();
        long totalProjects = projectRepository.count();
        long totalEndpoints = apiEndpointRepository.count();

        return new AdminStatsResponse(
                totalUsers,
                totalProjects,
                totalEndpoints
        );
    }

    private AdminUserResponse mapUser(User user) {

        return new AdminUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    private AdminProjectResponse mapProject(Project project) {

        return new AdminProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getBaseUrl(),
                project.getOwner().getId(),
                project.getOwner().getEmail()
        );
    }
}