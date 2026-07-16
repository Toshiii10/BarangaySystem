package com.barangay.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ResidentRepository residentRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public DataSeeder(UserRepository userRepository, ResidentRepository residentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.residentRepository = residentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Seed the Admin Account
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserAccount defaultAdmin = new UserAccount();
            defaultAdmin.setUsername("admin");
            defaultAdmin.setPassword(passwordEncoder.encode("admin123")); 
            defaultAdmin.setRole(Role.ADMIN);
            
            userRepository.save(defaultAdmin);
            System.out.println("=========================================");
            System.out.println("DEFAULT ADMIN ACCOUNT CREATED!");
            System.out.println("Username: admin | Password: admin123");
        }

        // 2. Seed a Test Resident Account
        if (userRepository.findByUsername("juan123").isEmpty()) {
            
            // First, create the physical resident profile
            Resident testResident = new Resident();
            testResident.setFirstName("Juan");
            testResident.setLastName("Dela Cruz");
            testResident.setZone("Purok 1");
            testResident.setCivilStatus("Single");
            
            Resident savedResident = residentRepository.save(testResident);

            // Second, create the login account and link it to the profile
            UserAccount residentUser = new UserAccount();
            residentUser.setUsername("juan123");
            residentUser.setPassword(passwordEncoder.encode("password123")); 
            residentUser.setRole(Role.RESIDENT);
            residentUser.setResident(savedResident); // Linking the ID
            
            userRepository.save(residentUser);
            System.out.println("DEFAULT RESIDENT ACCOUNT CREATED!");
            System.out.println("Username: juan123 | Password: password123");
            System.out.println("=========================================");
        }
    }
}