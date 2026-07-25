package com.reshu.apiforge.repository;

import com.reshu.apiforge.entity.ApiEndpoint;
import com.reshu.apiforge.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiEndpointRepository
        extends JpaRepository<ApiEndpoint, Long> {

    List<ApiEndpoint> findByProject(Project project);

    Optional<ApiEndpoint> findByIdAndProject(
            Long id,
            Project project
    );
}