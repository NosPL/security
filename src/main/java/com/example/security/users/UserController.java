package com.example.security.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.security.users.ControllerMappings.USERS;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(USERS)
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

    @DeleteMapping
    ResponseEntity deleteByUserName(@RequestParam String username) {
        userService.deleteByUsername(username);
        return new ResponseEntity(OK);
    }
}