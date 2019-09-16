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
    private Tracer tracer;

    @Autowired
    private UserRepository userRepository;

    private static void randomFailure(String source) throws InterruptedException {
        // Random behavior to generate latency and errors
        Thread.sleep(1 + (long) (Math.random() * 500));
        if (Math.random() > 0.9) {
//            throw new RuntimeException(source);
        }
    }

    @GetMapping()
    public Iterable<User> getUsers(@RequestHeader HttpHeaders headers) throws InterruptedException {
        // https://github.com/yurishkuro/opentracing-tutorial/tree/master/java/src/main/java/lesson02
        // https://github.com/yurishkuro/opentracing-tutorial/blob/fcf83f0d290a7ef6f8c0d2401d621a00f16b19c9/java/src/main/java/lib/Tracing.java
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-getUsers").asChildOf(parentSpanCtx).start();
        LOGGER.info("get users");
        randomFailure("getUsers");
        span.finish();
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@RequestHeader HttpHeaders headers, @PathVariable("id") String id) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-getUser").asChildOf(parentSpanCtx).start();
        LOGGER.info("get user with id: " + id);
        randomFailure("getUser");
        span.finish();
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestHeader HttpHeaders headers, @RequestBody User user) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-addUser").asChildOf(parentSpanCtx).start();
        LOGGER.info("adding user: " + user.toString());
        randomFailure("addUser");
        span.finish();
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestHeader HttpHeaders headers, @RequestBody User user, @PathVariable String id) throws InterruptedException {
        SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("phone-book-manager-updateUser").asChildOf(parentSpanCtx).start();
        LOGGER.info("update user: " + user.toString());
        randomFailure("updateUser");
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
        randomFailure("delete");
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
        span.finish();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUserNotFound(UserNotFoundException unfe) {
    }
}
