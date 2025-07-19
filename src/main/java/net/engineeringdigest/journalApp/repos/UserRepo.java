package net.engineeringdigest.journalApp.repos;

import net.engineeringdigest.journalApp.entity.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntry, Long> {
    UserEntry findByUserName(String userName);
}