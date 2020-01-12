package com.example.security.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaRepositories
public class UserConfig {

    @Bean
    public UserService userService(UserRepo userRepo) {
        return new UserServiceImpl(userRepo, passwordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
        return (UserDetailsService) userService(userRepo);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
