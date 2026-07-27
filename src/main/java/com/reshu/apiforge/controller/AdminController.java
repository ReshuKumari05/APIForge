package com.reshu.apiforge.controller;

import com.reshu.apiforge.dto.AdminProjectResponse;
import com.reshu.apiforge.dto.AdminStatsResponse;
import com.reshu.apiforge.dto.AdminUserResponse;
import com.reshu.apiforge.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }

    @GetMapping("/projects")
    public ResponseEntity<List<AdminProjectResponse>> getAllProjects() {

        return ResponseEntity.ok(
                adminService.getAllProjects()
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStatsResponse> getStats() {

        return ResponseEntity.ok(
                adminService.getStats()
        );
    }
}