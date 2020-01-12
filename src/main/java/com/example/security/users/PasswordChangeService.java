package com.example.security.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    void changePassword(String newPassword) {
        User loggedUser = getCurrentlyLoggedUser();
        String encodedPassword = passwordEncoder.encode(newPassword);
        loggedUser.setPassword(encodedPassword);
        userRepo.save(loggedUser);
    }

    private User getCurrentlyLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
