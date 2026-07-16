package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    
    // Checks if this resident already has an active request for this document
    boolean existsByResidentIdAndDocumentTypeAndStatus(Long residentId, String documentType, String status);
    
    // Required for DashboardController stats
    long countByStatus(String status);

    // Required for DocumentRequestService pending requests
    List<DocumentRequest> findByStatus(String status);
}