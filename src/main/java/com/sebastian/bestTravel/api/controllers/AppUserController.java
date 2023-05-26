package com.sebastian.bestTravel.api.controllers;

import com.sebastian.bestTravel.infrastructure.abstract_services.ModifiedUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "User")
public class AppUserController {
    private final ModifiedUserService modifiedUserService;

    @PatchMapping("/enabled")
    @Operation(summary = "Enabled or disabled user")
    public ResponseEntity<Map<String, Boolean>> enabledOrDisabled(@RequestParam String username) {
        return ResponseEntity.ok(modifiedUserService.enabled(username));
    }

    @PatchMapping("/addRole")
    @Operation(summary = "Add role user")
    public ResponseEntity<Map<String, Set<String>>> addRole(@RequestParam String username, @RequestParam String role) {
        return ResponseEntity.ok(modifiedUserService.addRole(username, role));
    }

    @PatchMapping("/removeRole")
    @Operation(summary = "Remove role user")
    public ResponseEntity<Map<String, Set<String>>> removeRole(@RequestParam String username, @RequestParam String role) {
        return ResponseEntity.ok(modifiedUserService.removeRole(username, role));
    }
}