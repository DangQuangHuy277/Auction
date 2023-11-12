package com.auction.app.user.controller;

import com.auction.app.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("users/statistical")
    public ResponseEntity<?> getStatistical(){
        return ResponseEntity.ok(adminService.getStatistical());
    }
}
