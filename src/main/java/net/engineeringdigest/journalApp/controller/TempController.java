package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
public class TempController {
    // This is a temporary controller for testing purposes

     @GetMapping("/test")
     public String testEndpoint() {
         return "This is a test endpoint!";
     }
}
