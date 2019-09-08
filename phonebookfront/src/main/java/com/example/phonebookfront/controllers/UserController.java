package com.example.phonebookfront.controllers;

import com.example.phonebookfront.client.GatewayClient;
import com.example.phonebookfront.model.User;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
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

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    Tracer tracer;

    @Autowired
    GatewayClient gatewayClient;

    @GetMapping("/")
    public String index(@RequestHeader HttpHeaders headers, Model model) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-frontend-getUsers").asChildOf(parentSpanCtx).start();
        LOGGER.info("index");

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed index");
        }

        model.addAttribute("users", gatewayClient.getUsers());
        span.finish();
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(@RequestHeader HttpHeaders headers, User user) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-frontend-getUsers").asChildOf(parentSpanCtx).start();
        LOGGER.info("showSignupForm");

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed signup");
        }

        span.finish();
        return "add-user";
    }

    @PostMapping
    public String addUser(@RequestHeader HttpHeaders headers, @Valid User user, BindingResult result, Model model) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-frontend-addUser").asChildOf(parentSpanCtx).start();
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
        span.finish();
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@RequestHeader HttpHeaders headers, @PathVariable("id") String id, Model model) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-frontend-getUsers").asChildOf(parentSpanCtx).start();
        LOGGER.info("showUpdateForm: " + id);

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed showUpdateForm");
        }

        User user = gatewayClient.getUser(id);
        model.addAttribute("user", user);
        span.finish();
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@RequestHeader HttpHeaders headers, @PathVariable("id") String id, @Valid User user, BindingResult result, Model model) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-frontend-getUsers").asChildOf(parentSpanCtx).start();
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
        span.finish();
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@RequestHeader HttpHeaders headers, @PathVariable("id") String id, Model model) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-frontend-getUsers").asChildOf(parentSpanCtx).start();
        LOGGER.info("deleteUser: " + id);

        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
            throw new RuntimeException("Failed deleteUser");
        }

        gatewayClient.deleteUser(id);
        model.addAttribute("users", gatewayClient.getUsers());
        span.finish();
        return "index";
    }
}
