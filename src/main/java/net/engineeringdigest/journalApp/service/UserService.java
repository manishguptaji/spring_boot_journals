package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repos.UseRepoCustomImpl;
import net.engineeringdigest.journalApp.repos.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Value("${external.github.base-url}")
    private String BASE_URL_GITHUB;

    private final UserRepo userRepo;
    private final UseRepoCustomImpl userRepo2;
    private final RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder,
                       RestTemplate restTemplate,
                       UseRepoCustomImpl userRepo2) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.userRepo2 = userRepo2;
    }

    public void createNewUser(UserEntry user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("USER"); // Default role for new users
        userRepo.save(user);
        log.info("New user created: {}", user.getUserName());
    }

    @Transactional
    public List<UserEntry> getAllUsers() {
        return userRepo.findAll();
    }

    public UserEntry getUserByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public UserEntry getRoleByUserName(String userName) {
        return userRepo2.getUserDataUsingCriteria(userName);
    }

    public Object getGitHubUsers() {
        String githubUsersEndpoint = "users";
        String url = BASE_URL_GITHUB + githubUsersEndpoint;
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return response.getBody();
    }

}