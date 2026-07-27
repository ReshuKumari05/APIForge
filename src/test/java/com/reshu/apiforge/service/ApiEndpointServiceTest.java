package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.EndpointRequest;
import com.reshu.apiforge.dto.EndpointResponse;
import com.reshu.apiforge.entity.ApiEndpoint;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.exception.ResourceNotFoundException;
import com.reshu.apiforge.repository.ApiEndpointRepository;
import com.reshu.apiforge.repository.ApiExecutionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiEndpointServiceTest {

    @Mock
    private ApiEndpointRepository endpointRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private ApiExecutionRepository executionRepository;

    @InjectMocks
    private ApiEndpointService endpointService;

    private Project project;
    private ApiEndpoint endpoint;

    private final Long projectId = 1L;
    private final Long endpointId = 10L;
    private final String email = "test@apiforge.com";

    @BeforeEach
    void setUp() {

        project = new Project();
        project.setId(projectId);
        project.setName("Test Project");
        project.setBaseUrl("https://example.com");

        endpoint = new ApiEndpoint();
        endpoint.setMethod("GET");
        endpoint.setPath("/users");
        endpoint.setDescription("Get users");
        endpoint.setProject(project);
    }

    @Test
    void shouldCreateEndpoint() {

        EndpointRequest request = new EndpointRequest();

        request.setMethod("post");
        request.setPath("/users");
        request.setDescription("Create user");

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.save(any(ApiEndpoint.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        EndpointResponse response =
                endpointService.create(
                        projectId,
                        request,
                        email
                );

        assertNotNull(response);

        // Service should automatically convert method to uppercase
        assertEquals("POST", response.getMethod());
        assertEquals("/users", response.getPath());
        assertEquals("Create user", response.getDescription());
        assertEquals(projectId, response.getProjectId());

        verify(projectService)
                .getOwnedProject(projectId, email);

        verify(endpointRepository)
                .save(any(ApiEndpoint.class));
    }

    @Test
    void shouldGetAllEndpoints() {

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByProject(project))
                .thenReturn(List.of(endpoint));

        List<EndpointResponse> responses =
                endpointService.getAll(projectId, email);

        assertEquals(1, responses.size());
        assertEquals("GET", responses.get(0).getMethod());
        assertEquals("/users", responses.get(0).getPath());

        verify(endpointRepository)
                .findByProject(project);
    }

    @Test
    void shouldGetOwnedEndpoint() {

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByIdAndProject(
                endpointId,
                project))
                .thenReturn(Optional.of(endpoint));

        ApiEndpoint result =
                endpointService.getOwnedEndpoint(
                        projectId,
                        endpointId,
                        email
                );

        assertNotNull(result);
        assertEquals("GET", result.getMethod());
        assertEquals("/users", result.getPath());
    }

    @Test
    void shouldRejectMissingEndpoint() {

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByIdAndProject(
                endpointId,
                project))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> endpointService.getOwnedEndpoint(
                        projectId,
                        endpointId,
                        email
                )
        );
    }

    @Test
    void shouldUpdateEndpoint() {

        EndpointRequest request = new EndpointRequest();

        request.setMethod("put");
        request.setPath("/users/1");
        request.setDescription("Update user");

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByIdAndProject(
                endpointId,
                project))
                .thenReturn(Optional.of(endpoint));

        when(endpointRepository.save(endpoint))
                .thenReturn(endpoint);

        EndpointResponse response =
                endpointService.update(
                        projectId,
                        endpointId,
                        request,
                        email
                );

        assertEquals("PUT", response.getMethod());
        assertEquals("/users/1", response.getPath());
        assertEquals(
                "Update user",
                response.getDescription()
        );

        verify(endpointRepository).save(endpoint);
    }

    @Test
    void shouldDeleteEndpointAndItsExecutionHistory() {

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByIdAndProject(
                endpointId,
                project))
                .thenReturn(Optional.of(endpoint));

        endpointService.delete(
                projectId,
                endpointId,
                email
        );

        verify(executionRepository)
                .deleteByEndpoint(endpoint);

        verify(endpointRepository)
                .delete(endpoint);

        // Important: history must be removed before endpoint
        var inOrder =
                inOrder(
                        executionRepository,
                        endpointRepository
                );

        inOrder.verify(executionRepository)
                .deleteByEndpoint(endpoint);

        inOrder.verify(endpointRepository)
                .delete(endpoint);
    }
}