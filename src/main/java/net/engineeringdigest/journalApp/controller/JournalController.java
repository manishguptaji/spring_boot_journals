package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.util.ApiResponse;
import net.engineeringdigest.journalApp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<JournalEntry>> createJournalEntry(@RequestBody JournalEntry je) {
        String userName = SecurityUtils.getCurrentUsername();
        UserEntry user = userService.getUserByUserName(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }
        // 3. Set the user in the journal entry
        je.setUser(user);
        // 4. Save the journal entry
        JournalEntry saved = journalEntryService.saveJournalEntry(je);

        return ResponseEntity.ok(new ApiResponse<>(true, "Journal entry created", saved));
    }

    @GetMapping("/getAllEntries")
    public ResponseEntity<ApiResponse<List<JournalEntry>>> getAllEntries() {
        List<JournalEntry> entries = journalEntryService.getAllJournalEntries();

        if (entries.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>(false, "No journal entries found", Collections.emptyList())
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(false, "Entries fetched successfully", entries)
        );
    }

}
