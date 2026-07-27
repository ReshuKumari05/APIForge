package com.reshu.apiforge.service;

import com.reshu.apiforge.dto.ProjectRequest;
import com.reshu.apiforge.dto.ProjectResponse;
import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.entity.User;
import com.reshu.apiforge.exception.ResourceNotFoundException;
import com.reshu.apiforge.repository.ProjectRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectService projectService;

    private User user;
    private Project project;

    private final String email = "test@apiforge.com";

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail(email);

        project = new Project();
        project.setId(10L);
        project.setName("Test API");
        project.setDescription("APIForge test project");
        project.setBaseUrl("https://example.com");
        project.setOwner(user);
    }

    @Test
    void shouldCreateProject() {

        ProjectRequest request = new ProjectRequest();
        request.setName("Test API");
        request.setDescription("APIForge test project");
        request.setBaseUrl("https://example.com");

        when(userService.findByEmail(email))
                .thenReturn(user);

        when(projectRepository.save(any(Project.class)))
                .thenAnswer(invocation -> {

                    Project saved =
                            invocation.getArgument(0);

                    saved.setId(10L);

                    return saved;
                });

        ProjectResponse response =
                projectService.create(request, email);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Test API", response.getName());
        assertEquals(
                "https://example.com",
                response.getBaseUrl()
        );

        verify(userService).findByEmail(email);
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void shouldGetOwnedProject() {

        when(userService.findByEmail(email))
                .thenReturn(user);

        when(projectRepository.findByIdAndOwner(10L, user))
                .thenReturn(Optional.of(project));

        Project result =
                projectService.getOwnedProject(10L, email);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Test API", result.getName());

        verify(projectRepository)
                .findByIdAndOwner(10L, user);
    }

    @Test
    void shouldRejectProjectNotOwnedByUser() {

        when(userService.findByEmail(email))
                .thenReturn(user);

        when(projectRepository.findByIdAndOwner(99L, user))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        projectService.getOwnedProject(
                                99L,
                                email
                        )
        );

        verify(projectRepository)
                .findByIdAndOwner(99L, user);
    }

    @Test
    void shouldUpdateProject() {

        ProjectRequest request = new ProjectRequest();

        request.setName("Updated API");
        request.setDescription("Updated description");
        request.setBaseUrl("https://updated.com");

        when(userService.findByEmail(email))
                .thenReturn(user);

        when(projectRepository.findByIdAndOwner(10L, user))
                .thenReturn(Optional.of(project));

        when(projectRepository.save(project))
                .thenReturn(project);

        ProjectResponse response =
                projectService.update(
                        10L,
                        request,
                        email
                );

        assertEquals(
                "Updated API",
                response.getName()
        );

        assertEquals(
                "https://updated.com",
                response.getBaseUrl()
        );

        verify(projectRepository).save(project);
    }

    @Test
    void shouldDeleteOwnedProject() {

        when(userService.findByEmail(email))
                .thenReturn(user);

        when(projectRepository.findByIdAndOwner(10L, user))
                .thenReturn(Optional.of(project));

        projectService.delete(10L, email);

        verify(projectRepository).delete(project);
    }
}