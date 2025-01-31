package com.controllers;

import com.DTOs.CreateUserDTO;
import com.DTOs.UpdateUserDTO;
import com.models.User;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.CacheRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser (@RequestBody CreateUserDTO createUserDTO){

        var userId = userService.createUser(createUserDTO);

        return ResponseEntity.created(URI.create("/v1/users/" + userId)).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        var users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable("userId") Long userId) throws Exception {

        var user = userService.getUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } throw new Exception("Usuario não encontrado");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) throws Exception {

        var user = userService.getUserById(userId);

        if (user.isPresent()) {
            userService.deleteUser(userId);

        return ResponseEntity.ok().build();
        } throw new Exception("Usuario não encontrado");
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserDTO updateUserDTO) throws Exception {

        var user = userService.getUserById(userId);

        if (user.isPresent()) {
            userService.updateUser(userId, updateUserDTO);

            return ResponseEntity.ok().build();
        } throw new Exception("Usuario não encontrado");
    }
}
