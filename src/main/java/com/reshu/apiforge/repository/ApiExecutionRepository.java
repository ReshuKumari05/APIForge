package com.reshu.apiforge.repository;

import com.reshu.apiforge.entity.ApiEndpoint;
import com.reshu.apiforge.entity.ApiExecution;
import com.reshu.apiforge.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiExecutionRepository
        extends JpaRepository<ApiExecution, Long> {

    List<ApiExecution> findByEndpointProject(Project project);

    List<ApiExecution> findByEndpointProjectOrderByExecutedAtDesc(
            Project project
    );

    List<ApiExecution> findByEndpointOrderByExecutedAtDesc(
            ApiEndpoint endpoint
    );

    void deleteByEndpoint(ApiEndpoint endpoint);
}