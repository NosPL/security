package com.example.security.users;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsersDto();

    void createOrUpdate(@Valid User user);

    UserDto getCurrentlyLoggedUserDto();

    void deleteByUsername(String username);

    void changePassword(String newPassword);
}
