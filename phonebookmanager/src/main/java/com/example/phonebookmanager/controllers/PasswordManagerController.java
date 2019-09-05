package com.example.phonebookmanager.controllers;

import com.example.phonebookmanager.exceptions.UserNotFoundException;
import com.example.phonebookmanager.model.User;
import com.example.phonebookmanager.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phonebookmanager/users")
public class PasswordManagerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordManagerController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public Iterable<User> getUsers() throws InterruptedException {
        LOGGER.info("get users");

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.8) {
            LOGGER.info("Failed get users");
            throw new RuntimeException("Failed getUsers");
        }

        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) throws InterruptedException {
        LOGGER.info("get user with id: " + id);

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.8) {
            throw new RuntimeException("Failed getUser");
        }

        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) throws InterruptedException {

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.8) {
            throw new RuntimeException("Failed addUser");
        }
        LOGGER.info("adding user: " + user.toString());

        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable String id) throws InterruptedException {
        LOGGER.info("update user: " + user.toString());

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.8) {
            throw new RuntimeException("Failed updateUser");
        }

        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) throws InterruptedException {
        LOGGER.info("delete user: " + id);

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.8) {
            throw new RuntimeException("Failed getUsers");
        }

        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUserNotFound(UserNotFoundException unfe) {
    }
}
