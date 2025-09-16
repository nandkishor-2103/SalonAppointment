package com.nandkishor.controller;


import com.nandkishor.model.User;
import com.nandkishor.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/users")
    public User createUser(@RequestBody @Valid User user) {
        return userRepository.save(user);
    }

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{userId}")
    public User getUserById(@PathVariable("userId") Long id) throws Exception {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isPresent()) {
            return otp.get();
        }

        throw new Exception("user not found");
    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) throws Exception {

        Optional<User> otp = userRepository.findById(id);

        if(otp.isEmpty()) {
            throw new Exception("user not exist with id " + id);
        }

        User existingUser = otp.get();

        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUserById(@PathVariable Long id) throws Exception {

        Optional<User> otp = userRepository.findById(id);

        if(otp.isEmpty()) {
            throw new Exception("user not exist with id " + id);
        }

        userRepository.deleteById(otp.get().getId());

        return "user deleted";
    }

}
