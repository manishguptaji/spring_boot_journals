package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.util.ApiResponse;
import net.engineeringdigest.journalApp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        // Default constructor
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserEntry>>> getAllUsers() {
        List<UserEntry> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>(false, "No users found", Collections.emptyList())
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(false, "Users fetched successfully", users)
        );
    }

    @GetMapping("/getUserByUserName")
    public ResponseEntity<ApiResponse<UserEntry>> getUserByUserName() {
        String userName = SecurityUtils.getCurrentUsername();

        if (userName == null || userName.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>(false, "User name is empty", null)
            );
        }

        UserEntry user = userService.getUserByUserName(userName);

        if (user == null) {
            return ResponseEntity.ok(
                    new ApiResponse<>(false, "User not found", null)
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User fetched successfully", user)
        );
    }

    @GetMapping("/getRoleByUserName")
    public ResponseEntity<ApiResponse<String>> getRoleByUserName() {
        String userName = SecurityUtils.getCurrentUsername();

        if (userName == null || userName.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>(false, "User name is empty", null)
            );
        }

        UserEntry user = userService.getRoleByUserName(userName);

        if (user == null) {
            return ResponseEntity.ok(
                    new ApiResponse<>(false, "User not found", null)
            );
        }

        String role = user.getRoles();
        return ResponseEntity.ok(
                new ApiResponse<>(false, "Role fetched successfully", role)
        );
    }

}
