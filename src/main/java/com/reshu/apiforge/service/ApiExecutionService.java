package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.ExecuteResponse;
import com.reshu.apiforge.entity.ApiEndpoint;
import com.reshu.apiforge.entity.ApiExecution;
import com.reshu.apiforge.repository.ApiExecutionRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Service
public class ApiExecutionService {

    private final ApiEndpointService endpointService;
    private final ApiExecutionRepository executionRepository;
    private final RestClient restClient;

    public ApiExecutionService(
            ApiEndpointService endpointService,
            ApiExecutionRepository executionRepository) {

        this.endpointService = endpointService;
        this.executionRepository = executionRepository;
        this.restClient = RestClient.create();
    }

    public ExecuteResponse execute(
            Long projectId,
            Long endpointId,
            String email) {

        ApiEndpoint endpoint =
                endpointService.getOwnedEndpoint(
                        projectId,
                        endpointId,
                        email
                );

        String url = buildUrl(
                endpoint.getProject().getBaseUrl(),
                endpoint.getPath()
        );

        HttpMethod method;

        try {
            method = HttpMethod.valueOf(
                    endpoint.getMethod().toUpperCase()
            );
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Unsupported HTTP method: "
                            + endpoint.getMethod()
            );
        }

        long startTime = System.currentTimeMillis();

        try {

            ResponseEntity<String> response =
                    restClient
                            .method(method)
                            .uri(url)
                            .retrieve()
                            .toEntity(String.class);

            long responseTime =
                    System.currentTimeMillis() - startTime;

            int statusCode =
                    response.getStatusCode().value();

            boolean successful =
                    response.getStatusCode().is2xxSuccessful();

            ApiExecution execution = saveExecution(
                    endpoint,
                    statusCode,
                    responseTime,
                    successful
            );

            return new ExecuteResponse(
                    execution.getId(),
                    url,
                    endpoint.getMethod(),
                    statusCode,
                    responseTime,
                    successful,
                    response.getBody()
            );

        } catch (org.springframework.web.client.HttpStatusCodeException ex) {

            long responseTime =
                    System.currentTimeMillis() - startTime;

            int statusCode =
                    ex.getStatusCode().value();

            ApiExecution execution = saveExecution(
                    endpoint,
                    statusCode,
                    responseTime,
                    false
            );

            return new ExecuteResponse(
                    execution.getId(),
                    url,
                    endpoint.getMethod(),
                    statusCode,
                    responseTime,
                    false,
                    ex.getResponseBodyAsString()
            );

        } catch (Exception ex) {

            long responseTime =
                    System.currentTimeMillis() - startTime;

            ApiExecution execution = saveExecution(
                    endpoint,
                    0,
                    responseTime,
                    false
            );

            return new ExecuteResponse(
                    execution.getId(),
                    url,
                    endpoint.getMethod(),
                    0,
                    responseTime,
                    false,
                    "Request failed: " + ex.getMessage()
            );
        }
    }

    private ApiExecution saveExecution(
            ApiEndpoint endpoint,
            int statusCode,
            long responseTime,
            boolean successful) {

        ApiExecution execution = new ApiExecution();

        execution.setEndpoint(endpoint);
        execution.setStatusCode(statusCode);
        execution.setResponseTimeMs(responseTime);
        execution.setSuccessful(successful);
        execution.setExecutedAt(LocalDateTime.now());

        return executionRepository.save(execution);
    }

    private String buildUrl(
            String baseUrl,
            String path) {

        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException(
                    "Project base URL is required to execute an endpoint"
            );
        }

        String cleanBase =
                baseUrl.endsWith("/")
                        ? baseUrl.substring(0, baseUrl.length() - 1)
                        : baseUrl;

        String cleanPath =
                path.startsWith("/")
                        ? path
                        : "/" + path;

        return cleanBase + cleanPath;
    }
}