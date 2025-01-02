//---------------------
package com.group2.PMS.controller;

import com.group2.PMS.model.User;
import com.group2.PMS.service.UserService;
import com.group2.PMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    // Register User (Admin or Customer)
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Normalize the role (ensure uppercase and trimmed)
        String role = user.getRole().toUpperCase().trim();
        if (role.equals("ADMIN") || role.equals("CUSTOMER")) {
            user.setRole(role); // Set role as ADMIN or CUSTOMER
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid role
        }

        User registeredUser = userService.registerUser(user); // Register user
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // Login User (Admin or Customer)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Check if user exists before authentication
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");  // Unauthorized if user doesn't exist
        }

        User user = userOpt.get();
        boolean isAuthenticated = userService.loginUser(email, password);  // Authenticate the user

        if (isAuthenticated) {
            String role = user.getRole().toUpperCase().trim();  // Ensure role is in uppercase

            // Role-based response
            if ("ADMIN".equals(role)) {
                return ResponseEntity.ok("Admin logged in successfully");
            } else if ("CUSTOMER".equals(role)) {
                return ResponseEntity.ok("Customer logged in successfully");
            } else {
                return ResponseEntity.status(403).body("Unauthorized role");  // Forbidden if role is unrecognized
            }
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");  // Unauthorized if authentication fails
        }
    }


    /**@PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Check if user exists before authentication
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");  // Unauthorized if user doesn't exist
        }

        User user = userOpt.get();
        boolean isAuthenticated = userService.loginUser(email, password);  // Authenticate the user

        if (isAuthenticated) {
            String role = user.getRole().toUpperCase().trim();  // Ensure role is in uppercase

            // Role-based response
            if ("ADMIN".equals(role)) {
                return ResponseEntity.ok("Admin logged in successfully");
            } else if ("CUSTOMER".equals(role)) {
                return ResponseEntity.ok("Customer logged in successfully");
            } else {
                return ResponseEntity.status(403).body("Unauthorized role");  // Forbidden if role is unrecognized
            }
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");  // Unauthorized if authentication fails
        }
    }*/

//---------------added

}


