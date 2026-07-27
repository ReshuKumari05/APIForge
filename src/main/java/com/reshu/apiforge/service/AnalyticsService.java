package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.AnalyticsResponse;
import com.reshu.apiforge.entity.ApiExecution;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.repository.ApiEndpointRepository;
import com.reshu.apiforge.repository.ApiExecutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final ProjectService projectService;
    private final ApiEndpointRepository endpointRepository;
    private final ApiExecutionRepository executionRepository;

    public AnalyticsService(
            ProjectService projectService,
            ApiEndpointRepository endpointRepository,
            ApiExecutionRepository executionRepository) {

        this.projectService = projectService;
        this.endpointRepository = endpointRepository;
        this.executionRepository = executionRepository;
    }

    public AnalyticsResponse getProjectAnalytics(
            Long projectId,
            String email) {

        Project project =
                projectService.getOwnedProject(projectId, email);

        long totalEndpoints =
                endpointRepository.findByProject(project).size();

        List<ApiExecution> executions =
                executionRepository.findByEndpointProject(project);

        long totalExecutions = executions.size();

        long successfulExecutions =
                executions.stream()
                        .filter(ApiExecution::getSuccessful)
                        .count();

        long failedExecutions =
                totalExecutions - successfulExecutions;

        double successRate =
                totalExecutions == 0
                        ? 0.0
                        : ((double) successfulExecutions
                        / totalExecutions) * 100;

        double averageResponseTime =
                executions.stream()
                        .mapToLong(ApiExecution::getResponseTimeMs)
                        .average()
                        .orElse(0.0);

        return new AnalyticsResponse(
                totalEndpoints,
                totalExecutions,
                successfulExecutions,
                failedExecutions,
                round(successRate),
                round(averageResponseTime)
        );
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}