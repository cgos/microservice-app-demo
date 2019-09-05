package com.example.phonebookfront.controllers;

import com.example.phonebookfront.client.GatewayClient;
import com.example.phonebookfront.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    GatewayClient gatewayClient;

    @GetMapping("/")
    public String index(Model model) throws InterruptedException {
        LOGGER.info("index");

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed index");
        }

        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) throws InterruptedException {
        LOGGER.info("showSignupForm");

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed signup");
        }

        return "add-user";
    }

    @PostMapping
    public String addUser(@Valid User user, BindingResult result, Model model) throws InterruptedException {
        LOGGER.info("addUser " + user.toString());

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed addUser");
        }

        if (result.hasErrors()) {
            return "add-user";
        }

        gatewayClient.create(user);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) throws InterruptedException {
        LOGGER.info("showUpdateForm: " + id);

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed showUpdateForm");
        }

        User user = gatewayClient.getUser(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id, @Valid User user, BindingResult result, Model model) throws InterruptedException {
        LOGGER.info("updateUser: " + id);

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed updateUser");
        }

        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        gatewayClient.update(id, user);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) throws InterruptedException {
        LOGGER.info("deleteUser: " + id);

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed deleteUser");
        }

        gatewayClient.deleteUser(id);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }
}
