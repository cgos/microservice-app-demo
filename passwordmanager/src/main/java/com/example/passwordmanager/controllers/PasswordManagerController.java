package com.example.passwordmanager.controllers;

import com.example.passwordmanager.exceptions.UserNotFoundException;
import com.example.passwordmanager.model.User;
import com.example.passwordmanager.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/passwordmanager/users")
public class PasswordManagerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordManagerController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public Iterable<User> getUsers() {
        LOGGER.info("get users");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        LOGGER.info("get user with id: " + id);
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        LOGGER.info("adding user: " + user.toString());
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable String id) {
        LOGGER.info("update user: " + user.toString());
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        LOGGER.info("delete user: " + id);
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUserNotFound(UserNotFoundException unfe) {
    }
}
