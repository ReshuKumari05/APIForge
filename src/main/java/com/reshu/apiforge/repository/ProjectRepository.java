package com.reshu.apiforge.repository;

import com.reshu.apiforge.entity.Project;
import com.reshu.apiforge.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByOwner(User owner, Pageable pageable);

    Optional<Project> findByIdAndOwner(Long id, User owner);
    Page<Project> findByOwnerAndNameContainingIgnoreCase(
            User owner,
            String name,
            Pageable pageable
    );
}