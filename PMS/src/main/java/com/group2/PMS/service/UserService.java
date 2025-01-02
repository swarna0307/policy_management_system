//working without hashing
/**package com.group2.PMS.service;

import com.group2.PMS.model.User;
import com.group2.PMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a User (either Admin or Customer)
    public User registerUser(User user) {
        user.setDateOfRegistration(LocalDate.now()); // Set the registration date
        user.setIsActive(true); // Set the user as active
        return userRepository.save(user); // Save user to database
    }

    // Login User (either Admin or Customer)

    public boolean loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);  // Find user by email
        return user.isPresent() && user.get().getPassword().equals(password); // Validate email and password
    }

    // Fetch Role by Email
    public String getRoleByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() ? user.get().getRole() : null; // Return the role if user exists, else null
    }
}
-----------------------------------*/
package com.group2.PMS.service;

import com.group2.PMS.model.User;
import com.group2.PMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Optional;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // BCryptPasswordEncoder instance to handle password encryption
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a User (either Admin or Customer)
    public User registerUser(User user) {
        user.setDateOfRegistration(LocalDate.now()); // Set the registration date
        user.setIsActive(true); // Set the user as active
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt the password before saving
        return userRepository.save(user); // Save user to database
    }

    // Login User (either Admin or Customer)
    public boolean loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);  // Find user by email
        if (user.isPresent()) {
            return passwordEncoder.matches(password, user.get().getPassword()); // Compare hashed passwords
        }
        return false; // Return false if user doesn't exist
    }

    // Fetch Role by Email
    public Optional<String> getRoleByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::getRole); // Return Optional role
    }

    //added

}
