package com.example.security.users;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String userName);

    List<User> findByAuthorities(List<UserAuthority> userAuthority);

    boolean deleteByEmail(String username);
}
