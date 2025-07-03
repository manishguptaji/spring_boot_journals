package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repos.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepository;

    public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
        // Logic to save the journal entry
        // This is a placeholder for the actual implementation
        journalEntryRepository.save(journalEntry);
        return journalEntry;
    }

    public List<JournalEntry> getAllJournalEntries() {
        // Logic to retrieve all journal entries
        // This is a placeholder for the actual implementation
        return journalEntryRepository.findAll();
    }

}
