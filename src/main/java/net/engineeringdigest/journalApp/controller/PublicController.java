package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.service.UserService;
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
    public void createNewUser(@RequestBody UserModel user) {
        // 1. Convert UserModel to UserEntity
        UserEntry userEntityObject = new UserEntry();
        userEntityObject.setUserName(user.getUserName());
        userEntityObject.setPassword(user.getPassword());
        userService.createNewUser(userEntityObject);
    }
}
