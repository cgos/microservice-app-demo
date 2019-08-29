package com.example.phonebookfront.client;

import com.example.phonebookfront.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "phonebookmanager", url = "http://localhost:8080/phonebookmanager/users")
public interface GatewayClient {

    @GetMapping()
    List<User> getUsers();

    @GetMapping("/{id}")
    User getUser(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.POST)
    User create(@RequestBody User user);

    @PutMapping("/{id}")
    User update(@PathVariable("id") String id, User user);

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") String id);
}
