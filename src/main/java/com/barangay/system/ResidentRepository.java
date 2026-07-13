package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    
    // Spring Boot is smart enough to generate the SQL for this just by the method name!
    // This is equivalent to: SELECT * FROM residents WHERE zone = ?
    List<Resident> findByZone(String zone);
    
    // We can add more custom searches later, like findByLastName
}