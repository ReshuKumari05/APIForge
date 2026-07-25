

package com.reshu.apiforge.controller;

import com.reshu.apiforge.dto.ProjectRequest;
import com.reshu.apiforge.dto.ProjectResponse;
import com.reshu.apiforge.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(
            @Valid @RequestBody ProjectRequest request,
            Authentication authentication) {

        ProjectResponse project =
                projectService.create(
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(project);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAll(
            Authentication authentication) {

        return ResponseEntity.ok(
                projectService.getAll(authentication.getName())
        );
    }

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
}