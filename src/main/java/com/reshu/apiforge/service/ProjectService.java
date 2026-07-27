

package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.ProjectRequest;
import com.reshu.apiforge.dto.ProjectResponse;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.entity.User;
import com.reshu.apiforge.repository.ProjectRepository;
import org.springframework.stereotype.Service;


import com.reshu.apiforge.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectService(
            ProjectRepository projectRepository,
            UserService userService) {

        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public Project getOwnedProject(Long id, String email) {

        User user = userService.findByEmail(email);

        return projectRepository
                .findByIdAndOwner(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));
    }

    public ProjectResponse create(
            ProjectRequest request,
            String email) {

        User user = userService.findByEmail(email);

        Project project = new Project();

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setBaseUrl(request.getBaseUrl());
        project.setOwner(user);

        Project saved = projectRepository.save(project);

        return toResponse(saved);
    }

    public Page<ProjectResponse> getAll(
            String email,
            int page,
            int size) {

        User user = userService.findByEmail(email);

        Pageable pageable = PageRequest.of(page, size);

        return projectRepository
                .findByOwner(user, pageable)
                .map(this::toResponse);
    }

    public ProjectResponse getById(
            Long id,
            String email) {

        User user = userService.findByEmail(email);

        Project project = projectRepository
                .findByIdAndOwner(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));

        return toResponse(project);
    }

    public ProjectResponse update(
            Long id,
            ProjectRequest request,
            String email) {

        User user = userService.findByEmail(email);

        Project project = projectRepository
                .findByIdAndOwner(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setBaseUrl(request.getBaseUrl());

        return toResponse(projectRepository.save(project));
    }

    public void delete(Long id, String email) {

        User user = userService.findByEmail(email);

        Project project = projectRepository
                .findByIdAndOwner(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));

        projectRepository.delete(project);
    }

    public Page<ProjectResponse> search(
            String email,
            String keyword,
            int page,
            int size) {

        User user = userService.findByEmail(email);

        Pageable pageable = PageRequest.of(page, size);

        return projectRepository
                .findByOwnerAndNameContainingIgnoreCase(
                        user,
                        keyword,
                        pageable
                )
                .map(this::toResponse);
    }

    private ProjectResponse toResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getBaseUrl()
        );
    }
}