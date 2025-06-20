package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void createJournalEntry(@RequestBody UserEntry user) {
        userService.createNewUser(user);
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
    public ResponseEntity<ApiResponse<UserEntry>> getUserByUserName(@RequestParam String userName) {
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

}
