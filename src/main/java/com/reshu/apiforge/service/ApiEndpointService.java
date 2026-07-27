package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.EndpointRequest;
import com.reshu.apiforge.dto.EndpointResponse;
import com.reshu.apiforge.entity.ApiEndpoint;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.repository.ApiEndpointRepository;
import org.springframework.stereotype.Service;
import com.reshu.apiforge.exception.ResourceNotFoundException;
import com.reshu.apiforge.repository.ApiExecutionRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ApiEndpointService {

    private final ApiEndpointRepository endpointRepository;
    private final ProjectService projectService;
    private final ApiExecutionRepository executionRepository;

    public ApiEndpointService(
            ApiEndpointRepository endpointRepository,
            ProjectService projectService,
            ApiExecutionRepository executionRepository) {

        this.endpointRepository = endpointRepository;
        this.projectService = projectService;
        this.executionRepository = executionRepository;
    }

    public EndpointResponse create(
            Long projectId,
            EndpointRequest request,
            String email) {

        Project project =
                projectService.getOwnedProject(projectId, email);

        ApiEndpoint endpoint = new ApiEndpoint();

        endpoint.setMethod(request.getMethod().toUpperCase());
        endpoint.setPath(request.getPath());
        endpoint.setDescription(request.getDescription());
        endpoint.setProject(project);

        return toResponse(endpointRepository.save(endpoint));
    }

    public List<EndpointResponse> getAll(
            Long projectId,
            String email) {

        Project project =
                projectService.getOwnedProject(projectId, email);

        return endpointRepository
                .findByProject(project)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EndpointResponse update(
            Long projectId,
            Long endpointId,
            EndpointRequest request,
            String email) {

        Project project =
                projectService.getOwnedProject(projectId, email);

        ApiEndpoint endpoint = endpointRepository
                .findByIdAndProject(endpointId, project)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endpoint not found"));

        endpoint.setMethod(request.getMethod().toUpperCase());
        endpoint.setPath(request.getPath());
        endpoint.setDescription(request.getDescription());

        return toResponse(endpointRepository.save(endpoint));
    }

    @Transactional
    public void delete(
            Long projectId,
            Long endpointId,
            String email) {

        Project project =
                projectService.getOwnedProject(projectId, email);

        ApiEndpoint endpoint = endpointRepository
                .findByIdAndProject(endpointId, project)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endpoint not found"));

        executionRepository.deleteByEndpoint(endpoint);

        endpointRepository.delete(endpoint);
    }

    public ApiEndpoint getOwnedEndpoint(
            Long projectId,
            Long endpointId,
            String email) {

        Project project =
                projectService.getOwnedProject(projectId, email);

        return endpointRepository
                .findByIdAndProject(endpointId, project)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endpoint not found"));
    }

    private EndpointResponse toResponse(ApiEndpoint endpoint) {

        return new EndpointResponse(
                endpoint.getId(),
                endpoint.getMethod(),
                endpoint.getPath(),
                endpoint.getDescription(),
                endpoint.getProject().getId()
        );
    }
}
