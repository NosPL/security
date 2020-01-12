package com.example.security.users;

import com.example.security.security_config.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> all = userService.getAllUsersDto();
        if (all.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(all, OK);
    }

    @PostMapping
    ResponseEntity createUser(@Valid @RequestBody User user) {
        userService.createOrUpdate(user);
        return new ResponseEntity(OK);
    }

    @GetMapping("/logged")
    ResponseEntity<UserDto> getUser() {
        UserDto currentlyLogged = userService.getCurrentlyLoggedUserDto();
        return new ResponseEntity(currentlyLogged, OK);
    }

    @PostMapping("/logged/password-change")
    ResponseEntity changePassword(HttpServletRequest request) {
        String passwordChangeHeader = request.getHeader(SecurityConstants.PASSWORD_CHANGE);
        userService.changePassword(passwordChangeHeader);
        return new ResponseEntity(OK);
    }

    @DeleteMapping
    ResponseEntity deleteByUserName(@RequestParam String username) {
        userService.deleteByUsername(username);
        return new ResponseEntity(OK);
    }
}