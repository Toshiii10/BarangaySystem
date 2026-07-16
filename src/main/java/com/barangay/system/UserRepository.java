package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    
    // Spring Security will use this method to look up a user when they try to log in
    Optional<UserAccount> findByUsername(String username);
}