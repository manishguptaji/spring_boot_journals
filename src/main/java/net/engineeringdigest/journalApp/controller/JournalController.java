package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/create")
    public void createJournalEntry(@RequestBody JournalEntry je) {
        journalEntryService.saveJournalEntry(je);
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
