package com.reshu.apiforge.controller;

import com.reshu.apiforge.dto.EndpointRequest;
import com.reshu.apiforge.dto.EndpointResponse;
import com.reshu.apiforge.service.ApiEndpointService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/endpoints")
public class ApiEndpointController {

    private final ApiEndpointService endpointService;

    public ApiEndpointController(ApiEndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @PostMapping
    public ResponseEntity<EndpointResponse> create(
            @PathVariable Long projectId,
            @Valid @RequestBody EndpointRequest request,
            Authentication authentication) {

        EndpointResponse endpoint =
                endpointService.create(
                        projectId,
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(endpoint);
    }
    @PutMapping("/{endpointId}")
    public ResponseEntity<EndpointResponse> update(
            @PathVariable Long projectId,
            @PathVariable Long endpointId,
            @Valid @RequestBody EndpointRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                endpointService.update(
                        projectId,
                        endpointId,
                        request,
                        authentication.getName()
                )
        );
    }

    @DeleteMapping("/{endpointId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long projectId,
            @PathVariable Long endpointId,
            Authentication authentication) {

        endpointService.delete(
                projectId,
                endpointId,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EndpointResponse>> getAll(
            @PathVariable Long projectId,
            Authentication authentication) {

        return ResponseEntity.ok(
                endpointService.getAll(
                        projectId,
                        authentication.getName()
                )
        );
    }
}

