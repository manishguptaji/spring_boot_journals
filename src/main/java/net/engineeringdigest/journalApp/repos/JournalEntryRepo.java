package net.engineeringdigest.journalApp.repos;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepo extends JpaRepository<JournalEntry, Long> {
}
