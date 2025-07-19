package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;


    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendTestEmail() {
        emailService.sendEmail("ayushirustagi945@gmail.com", "hi Ayushi", "Hello from Spring Boot!");
        return ResponseEntity.ok("Email sent!");
    }
}
