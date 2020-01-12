package com.example.security.users;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return user;
    }

    @Override
    public List<UserDto> getAllUsersDto() {
        return userRepo
                .findAll()
                .stream()
                .map(UserDto::fromUser)
                .collect(toList());
    }

    @Override
    public void createOrUpdate(@Valid User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }

    @Override
    public UserDto getCurrentlyLoggedUserDto() {
        User currentlyLogged = getCurrentlyLoggedUser();
        return UserDto.fromUser(currentlyLogged);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepo.deleteByEmail(username);
    }

    @Override
    public void changePassword(String newPassword) {
        User loggedUser = getCurrentlyLoggedUser();
        String encodedPassword = passwordEncoder.encode(newPassword);
        loggedUser.setPassword(encodedPassword);
        userRepo.save(loggedUser);
    }

    private User getCurrentlyLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
