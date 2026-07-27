package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.AnalyticsResponse;
import com.reshu.apiforge.entity.ApiEndpoint;
import com.reshu.apiforge.entity.ApiExecution;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.repository.ApiEndpointRepository;
import com.reshu.apiforge.repository.ApiExecutionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private ApiEndpointRepository endpointRepository;

    @Mock
    private ApiExecutionRepository executionRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    private Project project;

    private final Long projectId = 1L;
    private final String email = "test@apiforge.com";

    @BeforeEach
    void setUp() {

        project = new Project();

        project.setId(projectId);
        project.setName("Analytics Test");
        project.setBaseUrl("https://example.com");
    }

    @Test
    void shouldReturnZeroAnalyticsWhenNoExecutionsExist() {

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByProject(project))
                .thenReturn(List.of());

        when(executionRepository.findByEndpointProject(project))
                .thenReturn(List.of());

        AnalyticsResponse response =
                analyticsService.getProjectAnalytics(
                        projectId,
                        email
                );

        assertEquals(0, response.getTotalEndpoints());
        assertEquals(0, response.getTotalExecutions());
        assertEquals(0, response.getSuccessfulExecutions());
        assertEquals(0, response.getFailedExecutions());

        assertEquals(
                0.0,
                response.getSuccessRate(),
                0.001
        );

        assertEquals(
                0.0,
                response.getAverageResponseTimeMs(),
                0.001
        );
    }

    @Test
    void shouldCalculateFiftyPercentSuccessRate() {

        ApiEndpoint endpoint1 = createEndpoint("GET", "/success");
        ApiEndpoint endpoint2 = createEndpoint("GET", "/failure");

        ApiExecution successful =
                createExecution(
                        endpoint1,
                        200,
                        100L,
                        true
                );

        ApiExecution failed =
                createExecution(
                        endpoint2,
                        404,
                        300L,
                        false
                );

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByProject(project))
                .thenReturn(
                        List.of(endpoint1, endpoint2)
                );

        when(executionRepository.findByEndpointProject(project))
                .thenReturn(
                        List.of(successful, failed)
                );

        AnalyticsResponse response =
                analyticsService.getProjectAnalytics(
                        projectId,
                        email
                );

        assertEquals(2, response.getTotalEndpoints());
        assertEquals(2, response.getTotalExecutions());

        assertEquals(
                1,
                response.getSuccessfulExecutions()
        );

        assertEquals(
                1,
                response.getFailedExecutions()
        );

        assertEquals(
                50.0,
                response.getSuccessRate(),
                0.001
        );

        // (100 + 300) / 2 = 200
        assertEquals(
                200.0,
                response.getAverageResponseTimeMs(),
                0.001
        );
    }

    @Test
    void shouldCalculateHundredPercentSuccessRate() {

        ApiEndpoint endpoint =
                createEndpoint("GET", "/users");

        ApiExecution execution1 =
                createExecution(
                        endpoint,
                        200,
                        100L,
                        true
                );

        ApiExecution execution2 =
                createExecution(
                        endpoint,
                        201,
                        200L,
                        true
                );

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByProject(project))
                .thenReturn(List.of(endpoint));

        when(executionRepository.findByEndpointProject(project))
                .thenReturn(
                        List.of(execution1, execution2)
                );

        AnalyticsResponse response =
                analyticsService.getProjectAnalytics(
                        projectId,
                        email
                );

        assertEquals(2, response.getTotalExecutions());

        assertEquals(
                2,
                response.getSuccessfulExecutions()
        );

        assertEquals(
                0,
                response.getFailedExecutions()
        );

        assertEquals(
                100.0,
                response.getSuccessRate(),
                0.001
        );

        assertEquals(
                150.0,
                response.getAverageResponseTimeMs(),
                0.001
        );
    }

    @Test
    void shouldCalculateTwentyFivePercentSuccessRate() {

        ApiEndpoint endpoint =
                createEndpoint("GET", "/test");

        ApiExecution execution1 =
                createExecution(
                        endpoint,
                        200,
                        100L,
                        true
                );

        ApiExecution execution2 =
                createExecution(
                        endpoint,
                        500,
                        200L,
                        false
                );

        ApiExecution execution3 =
                createExecution(
                        endpoint,
                        500,
                        300L,
                        false
                );

        ApiExecution execution4 =
                createExecution(
                        endpoint,
                        404,
                        400L,
                        false
                );

        when(projectService.getOwnedProject(projectId, email))
                .thenReturn(project);

        when(endpointRepository.findByProject(project))
                .thenReturn(List.of(endpoint));

        when(executionRepository.findByEndpointProject(project))
                .thenReturn(
                        List.of(
                                execution1,
                                execution2,
                                execution3,
                                execution4
                        )
                );

        AnalyticsResponse response =
                analyticsService.getProjectAnalytics(
                        projectId,
                        email
                );

        assertEquals(4, response.getTotalExecutions());
        assertEquals(1, response.getSuccessfulExecutions());
        assertEquals(3, response.getFailedExecutions());

        assertEquals(
                25.0,
                response.getSuccessRate(),
                0.001
        );

        // (100 + 200 + 300 + 400) / 4
        assertEquals(
                250.0,
                response.getAverageResponseTimeMs(),
                0.001
        );
    }

    private ApiEndpoint createEndpoint(
            String method,
            String path) {

        ApiEndpoint endpoint =
                new ApiEndpoint();

        endpoint.setMethod(method);
        endpoint.setPath(path);
        endpoint.setProject(project);

        return endpoint;
    }

    private ApiExecution createExecution(
            ApiEndpoint endpoint,
            int statusCode,
            long responseTime,
            boolean successful) {

        ApiExecution execution =
                new ApiExecution();

        execution.setEndpoint(endpoint);
        execution.setStatusCode(statusCode);
        execution.setResponseTimeMs(responseTime);
        execution.setSuccessful(successful);

        return execution;
    }
}