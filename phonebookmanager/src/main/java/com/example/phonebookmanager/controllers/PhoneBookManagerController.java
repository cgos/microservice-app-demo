package com.example.phonebookmanager.controllers;

import com.example.phonebookmanager.exceptions.UserNotFoundException;
import com.example.phonebookmanager.model.User;
import com.example.phonebookmanager.repositories.UserRepository;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phonebookmanager/users")
public class PhoneBookManagerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneBookManagerController.class);

    @Autowired
    protected Tracer tracer;

    @Autowired
    private UserRepository userRepository;

    private static void randomFailure(String source, Span span) throws InterruptedException {
        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
//            RuntimeException rte = new RuntimeException(source);
//            span.log("randomFailure" + rte.toString());
//            throw rte;
        }
    }

    @GetMapping()
    public Iterable<User> getUsers(@RequestHeader HttpHeaders headers) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-getUsers").asChildOf(parentSpanCtx).start();

        try {
            LOGGER.info("get users");
            randomFailure("getUsers", span);
            return userRepository.findAll();
        } finally {
            span.finish();
        }
    }

    @GetMapping("/{id}")
    public User getUser(@RequestHeader HttpHeaders headers, @PathVariable("id") String id) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-getUser").asChildOf(parentSpanCtx).start();
        try {
            LOGGER.info("get user with id: " + id);
            randomFailure("getUser", span);

            return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        } finally {
            span.finish();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestHeader HttpHeaders headers, @RequestBody User user) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-addUser").asChildOf(parentSpanCtx).start();
        try {
            LOGGER.info("adding user: " + user.toString());
            randomFailure("addUser", span);
            return userRepository.save(user);
        } finally {
            span.finish();
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestHeader HttpHeaders headers, @RequestBody User user, @PathVariable String id) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-updateUser").asChildOf(parentSpanCtx).start();
        LOGGER.info("update user: " + user.toString());
        randomFailure("updateUser", span);
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        span.finish();
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@RequestHeader HttpHeaders headers, @PathVariable("id") String id) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-deleteUser").asChildOf(parentSpanCtx).start();
        LOGGER.info("delete user: " + id);
        randomFailure("delete", span);
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
        span.finish();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUserNotFound(UserNotFoundException unfe) {
    }
}
