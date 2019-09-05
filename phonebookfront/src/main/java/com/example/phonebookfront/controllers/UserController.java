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
    public String index(Model model) {
        LOGGER.info("index");
        model.addAttribute("users",gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        LOGGER.info("showSignupForm");
        return "add-user";
    }

    @PostMapping
    public String addUser(@Valid User user, BindingResult result, Model model) {
        LOGGER.info("addUser " + user.toString());
        if (result.hasErrors()) {
            return "add-user";
        }

        gatewayClient.create(user);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        LOGGER.info("showUpdateForm: " + id);
        User user = gatewayClient.getUser(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id, @Valid User user, BindingResult result, Model model) {
        LOGGER.info("updateUser: " + id);
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        gatewayClient.update(id, user);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        LOGGER.info("deleteUser: " + id);
        gatewayClient.deleteUser(id);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }
}
