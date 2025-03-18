package edu.uscb.csci470sp25.multiuserverse_backend.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort; // needed for sorting list of users by ID
import org.springframework.web.bind.annotation.*;

import edu.uscb.csci470sp25.multiuserverse_backend.exception.UserNotFoundException;
import edu.uscb.csci470sp25.multiuserverse_backend.model.User;
import edu.uscb.csci470sp25.multiuserverse_backend.repository.UserRepository;

@RestController
// @CrossOrigin("http://localhost:5173") // <-- uncomment this when narrator codes @CrossOrigin in video #9
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Stub: Always throws UnsupportedOperationException (Test should fail initially)
    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }


    @GetMapping("/users")
    List<User> getAllUsers() {
    	return userRepository.findAll();
    }

    // Stub: Always throws UserNotFoundException (Test should fail when expecting user data)
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        throw new UserNotFoundException(id);
    }

    // Stub: Always throws UnsupportedOperationException (Test should fail when expecting update)
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    // Stub: Always throws UserNotFoundException (Test should fail when expecting deletion)
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        throw new UserNotFoundException(id);
    }

} // end class UserController
