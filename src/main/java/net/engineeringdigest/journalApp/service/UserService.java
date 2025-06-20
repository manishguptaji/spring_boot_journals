package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void createNewUser(UserEntry user) {
        userRepo.save(user);
    }

    @Transactional
    public List<UserEntry> getAllUsers() {
        return userRepo.findAll();
    }

    public UserEntry getUserByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

}