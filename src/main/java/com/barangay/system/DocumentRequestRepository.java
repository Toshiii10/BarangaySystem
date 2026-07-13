package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    
    // Custom method to find all documents requested by a specific resident
    List<DocumentRequest> findByResidentId(Long residentId);
    
    // Custom method to see all pending requests that the Barangay Captain needs to sign
    List<DocumentRequest> findByStatus(String status);
}