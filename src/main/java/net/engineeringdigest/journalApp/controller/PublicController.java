package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {


    private final UserService userService;

    public PublicController(
            UserService userService
    ) {
        this.userService = userService;
    }


    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse<String>> createNewUser(@RequestBody UserModel user) {
        UserEntry existingUser = userService.getUserByUserName(user.getUserName());
        if (existingUser != null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "User already exists", null));
        }
        UserEntry userEntityObject = new UserEntry();
        userEntityObject.setUserName(user.getUserName());
        userEntityObject.setPassword(user.getPassword());
        userService.createNewUser(userEntityObject);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User created successfully", null)
        );
    }

    @GetMapping("/github-users")
    public ResponseEntity<Object> getGitHubUsers() {
        return ResponseEntity.ok(userService.getGitHubUsers());
    }
}
