package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService service;

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> oldUser = service.findByUserName(userName);
        if (!oldUser.isPresent()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        User old = oldUser.get();
        old.setUserName(user.getUserName());
        old.setPassword(user.getPassword());
        service.saveUser(old);

        return new ResponseEntity<>(old, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> oldUser = service.findByUserName(userName);
        if (!oldUser.isPresent()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        service.deleteByUsername(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
