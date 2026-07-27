package com.reshu.apiforge.controller;

import com.reshu.apiforge.dto.ProjectRequest;
import com.reshu.apiforge.dto.ProjectResponse;
import com.reshu.apiforge.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.reshu.apiforge.dto.AnalyticsResponse;
import com.reshu.apiforge.service.AnalyticsService;
import com.reshu.apiforge.dto.ExecutionHistoryResponse;
import com.reshu.apiforge.service.ExecutionHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final AnalyticsService analyticsService;
    private final ExecutionHistoryService historyService;

    public ProjectController(
            ProjectService projectService,
            AnalyticsService analyticsService,
            ExecutionHistoryService historyService) {

        this.projectService = projectService;
        this.analyticsService = analyticsService;
        this.historyService = historyService;
    }

    // CREATE PROJECT
    @PostMapping
    public ResponseEntity<ProjectResponse> create(
            @Valid @RequestBody ProjectRequest request,
            Authentication authentication) {

        ProjectResponse project = projectService.create(
                request,
                authentication.getName()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(project);
    }

    // GET ALL PROJECTS WITH PAGINATION
    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        return ResponseEntity.ok(
                projectService.getAll(
                        authentication.getName(),
                        page,
                        size
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProjectResponse>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        return ResponseEntity.ok(
                projectService.search(
                        authentication.getName(),
                        keyword,
                        page,
                        size
                )
        );
    }

    // GET PROJECT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getById(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                projectService.getById(
                        id,
                        authentication.getName()
                )
        );
    }

    // UPDATE PROJECT
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                projectService.update(
                        id,
                        request,
                        authentication.getName()
                )
        );
    }

    // DELETE PROJECT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication) {

        projectService.delete(
                id,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/analytics")
    public ResponseEntity<AnalyticsResponse> analytics(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                analyticsService.getProjectAnalytics(
                        id,
                        authentication.getName()
                )
        );
    }
    @GetMapping("/{id}/history")
    public ResponseEntity<List<ExecutionHistoryResponse>> history(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                historyService.getProjectHistory(
                        id,
                        authentication.getName()
                )
        );
    }
}