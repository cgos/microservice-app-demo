package com.example.phonebookfront.controllers;

import com.example.phonebookfront.client.GatewayClient;
import com.example.phonebookfront.model.User;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    Tracer tracer;

    @Autowired
    GatewayClient gatewayClient;

    private static void randomFailure(String source) throws InterruptedException {
        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
//            throw new RuntimeException(source);
        }
    }

    @GetMapping("/")
    public String index(@RequestHeader HttpHeaders headers, Model model) throws InterruptedException {
        Map<String, String> headersMap = headers.toSingleValueMap();
        TextMap textMapAdapter = new TextMapAdapter(headersMap);
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, textMapAdapter);


        Tracer.SpanBuilder spanBuilder;
        if(parentSpanCtx ==  null) {
            spanBuilder = tracer.buildSpan("index");
        } else {
            spanBuilder = tracer.buildSpan("index").asChildOf(parentSpanCtx);
        }

        Span span = spanBuilder.start();

        LOGGER.info("index");
        randomFailure("index");
        model.addAttribute("users", gatewayClient.getUsers());

        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(@RequestHeader HttpHeaders headers, User user) throws InterruptedException {

        LOGGER.info("showSignupForm");

        randomFailure("signup");
        return "add-user";
    }

    @PostMapping
    public String addUser(@RequestHeader HttpHeaders headers, @Valid User user, BindingResult result, Model model) throws InterruptedException {

        LOGGER.info("addUser " + user.toString());
        randomFailure("addUser");
        if (result.hasErrors()) {
            return "add-user";
        }

        gatewayClient.create(user);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@RequestHeader HttpHeaders headers, @PathVariable("id") String id, Model model) throws InterruptedException {

        LOGGER.info("showUpdateForm: " + id);
        randomFailure("edit");
        User user = gatewayClient.getUser(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@RequestHeader HttpHeaders headers, @PathVariable("id") String id, @Valid User user, BindingResult result, Model model) throws InterruptedException {

        LOGGER.info("updateUser: " + id);
        randomFailure("update");
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        gatewayClient.update(id, user);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@RequestHeader HttpHeaders headers, @PathVariable("id") String id, Model model) throws InterruptedException {

        LOGGER.info("deleteUser: " + id);
        randomFailure("delete");
        gatewayClient.deleteUser(id);
        model.addAttribute("users", gatewayClient.getUsers());
        return "index";
    }
}
