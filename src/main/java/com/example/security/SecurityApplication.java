package com.example.security;

import com.example.security.users.User;
import com.example.security.users.UserAuthority;
import com.example.security.users.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.util.Arrays.asList;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }


    @Bean
    CommandLineRunner defaultUser(UserService userService) {
        return args -> {
            try {
                User user = new User("Jan", "Kowalski", "admin@gmail.com", "password", asList(UserAuthority.ADMIN, UserAuthority.COORDINATOR, UserAuthority.RECRUITER));
                userService.createOrUpdate(user);
            } catch (Exception e) {
            }
        };
    }
}
