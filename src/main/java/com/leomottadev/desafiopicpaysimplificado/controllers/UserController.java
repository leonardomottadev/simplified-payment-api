package com.leomottadev.desafiopicpaysimplificado.controllers;

import com.leomottadev.desafiopicpaysimplificado.domain.user.User;
import com.leomottadev.desafiopicpaysimplificado.dtos.UserDTO;
import com.leomottadev.desafiopicpaysimplificado.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
