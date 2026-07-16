package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    
    // Checks if a resident with the exact first and last name already exists
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    
    // Required for ResidentService
    List<Resident> findByZone(String zone);
}