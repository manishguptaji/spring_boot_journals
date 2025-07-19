package net.engineeringdigest.journalApp.repos;

import net.engineeringdigest.journalApp.entity.UserEntry;

public interface UserRepoCustom {
    UserEntry getUserDataUsingCriteria(String name);
}
