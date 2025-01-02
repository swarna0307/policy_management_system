package com.group2.PMS.repository;

import com.group2.PMS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (for login purpose)
    Optional<User> findByEmail(String email);

    // Find user by role and email (if needed)
    Optional<User> findByEmailAndRole(String email, String role);
}




