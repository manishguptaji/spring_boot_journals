package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.service.RedisService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.util.ApiResponse;
import net.engineeringdigest.journalApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        if (user.getUserName() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username or password missing");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            log.info("User {} authenticated successfully", user.getUserName());

            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("type", "Bearer");
            response.put("username", userDetails.getUsername());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Authentication failed for user: {}", user.getUserName(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + e.getMessage());
        }
    }


}
