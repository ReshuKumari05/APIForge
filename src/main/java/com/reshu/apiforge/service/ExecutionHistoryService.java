package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.ExecutionHistoryResponse;
import com.reshu.apiforge.entity.ApiEndpoint;
import com.reshu.apiforge.entity.ApiExecution;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.repository.ApiExecutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutionHistoryService {

    private final ProjectService projectService;
    private final ApiEndpointService endpointService;
    private final ApiExecutionRepository executionRepository;

    public ExecutionHistoryService(
            ProjectService projectService,
            ApiEndpointService endpointService,
            ApiExecutionRepository executionRepository) {

        this.projectService = projectService;
        this.endpointService = endpointService;
        this.executionRepository = executionRepository;
    }

    public List<ExecutionHistoryResponse> getProjectHistory(
            Long projectId,
            String email) {

        Project project =
                projectService.getOwnedProject(projectId, email);

        return executionRepository
                .findByEndpointProjectOrderByExecutedAtDesc(project)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ExecutionHistoryResponse> getEndpointHistory(
            Long projectId,
            Long endpointId,
            String email) {

        ApiEndpoint endpoint =
                endpointService.getOwnedEndpoint(
                        projectId,
                        endpointId,
                        email
                );

        return executionRepository
                .findByEndpointOrderByExecutedAtDesc(endpoint)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ExecutionHistoryResponse toResponse(
            ApiExecution execution) {

        ApiEndpoint endpoint =
                execution.getEndpoint();

        return new ExecutionHistoryResponse(
                execution.getId(),
                endpoint.getId(),
                endpoint.getMethod(),
                endpoint.getPath(),
                execution.getStatusCode(),
                execution.getResponseTimeMs(),
                execution.getSuccessful(),
                execution.getExecutedAt()
        );
    }
}