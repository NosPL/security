package com.example.security.users;

import lombok.Value;

import java.util.List;

@Value
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private List<UserAuthority> authorities;

    public static UserDto fromUser(User user) {
        return new UserDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getAuthorities());
    }
}
