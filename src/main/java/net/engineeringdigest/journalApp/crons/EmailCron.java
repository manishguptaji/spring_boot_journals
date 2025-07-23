package net.engineeringdigest.journalApp.crons;

import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailCron {

    private final EmailService emailService;

    private EmailCron(EmailService es) {
        // This constructor can be used to initialize any required components or services.kk
        this.emailService = es;
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void sendEmailCron() {
        //emailService.sendEmail("ayushirustagi945@gmail.com", "hi Ayushi", "Hello from Spring Boot!");
    }

}
