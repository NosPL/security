package com.example.security.users;

import com.example.security.security_config.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.security.users.ControllerMappings.LOGGED_USER;
import static com.example.security.users.ControllerMappings.LOGGED_USER_PASSWORD_CHANGE;
import static org.springframework.http.HttpStatus.OK;

@RestController(LOGGED_USER)
public class LoggedUserController {

    @Autowired
    UserService userService;

    @GetMapping
    ResponseEntity<UserDto> getUser() {
        UserDto currentlyLogged = userService.getCurrentlyLoggedUserDto();
        return new ResponseEntity(currentlyLogged, OK);
    }

    @PostMapping(LOGGED_USER_PASSWORD_CHANGE)
    ResponseEntity changePassword(HttpServletRequest request) {
        String passwordChangeHeader = request.getHeader(SecurityConstants.PASSWORD_CHANGE);
        userService.changePassword(passwordChangeHeader);
        return new ResponseEntity(OK);
    }
}
