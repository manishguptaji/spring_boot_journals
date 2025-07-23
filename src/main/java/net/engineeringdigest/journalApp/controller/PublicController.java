package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.service.RedisService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.util.ApiResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {


    private final UserService userService;
    private final RedisService redisService;
    private final RedisTemplate <String, Object> redisTemplate;

    public PublicController(
            UserService userService,
            RedisTemplate<String, Object> redisTemplate,
            RedisService redisService
    ) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.redisService = redisService;
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
        //check in redis first
        Object cachedUsers = redisService.getValue("githubUsers", Object.class);
        if (cachedUsers != null) {
            log.info("Returning cached GitHub users from Redis");
            return ResponseEntity.ok(cachedUsers);
        }
        // If not found in cache, fetch from service
        Object users = userService.getGitHubUsers();
        // Store the fetched users in Redis cache
        redisService.setValue("githubUsers", users, 1L); // Cache for 1 minutes
        // Return the users
        log.info("Fetched GitHub users from service and cached in Redis");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/redis/get/{key}")
    public String getRedisValue(@PathVariable String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

    @GetMapping("/redis/set")
    public String setRedisValue(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Value set successfully";
    }
}
