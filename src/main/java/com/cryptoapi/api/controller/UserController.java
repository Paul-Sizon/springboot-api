package com.cryptoapi.api.controller;

import com.cryptoapi.api.model.User;
import com.cryptoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/user")
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("{id}")
    public String getUser(@PathVariable("id") UUID id) {
        return userService.getUser(id).orElse(null).toString();
    }

    @DeleteMapping("{id}")
    public int deleteUser(@PathVariable("id") UUID id) {
        return userService.deleteUser(id);
    }

    @PutMapping("{id}")
    public int updateUser(@PathVariable("id") UUID id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}
