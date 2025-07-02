package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public void createNewUser(@RequestBody UserEntry user) {
        userService.createNewUser(user);
    }
}
