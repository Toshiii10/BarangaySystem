package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    
    // Spring Boot is smart enough to generate the SQL for this just by the method name!
    // This is equivalent to: SELECT * FROM residents WHERE zone = ?
    List<Resident> findByZone(String zone);
    
    // We can add more custom searches later, like findByLastName
}