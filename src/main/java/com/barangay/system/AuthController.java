package com.barangay.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

 @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        
        Optional<UserAccount> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isPresent()) {
            UserAccount user = userOpt.get();
            
            // NEW: Verify the selected dropdown role matches their actual database role
            if (!user.getRole().name().equalsIgnoreCase(loginRequest.getRole())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body("Account does not match the selected role.");
            }
            
            // Compare passwords
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("role", user.getRole());
                
                if (user.getResident() != null) {
                    response.put("residentId", user.getResident().getId());
                }
                
                return ResponseEntity.ok(response);
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}