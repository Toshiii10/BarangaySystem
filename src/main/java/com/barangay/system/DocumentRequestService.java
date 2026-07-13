package com.barangay.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentRequestService {

    @Autowired
    private DocumentRequestRepository documentRepository;

    @Autowired
    private ResidentRepository residentRepository;

    // 1. Create a request linked to a specific resident
    public DocumentRequest createRequest(Long residentId, DocumentRequest request) {
        // Find the resident in the database first
        Resident resident = residentRepository.findById(residentId)
            .orElseThrow(() -> new RuntimeException("Error: Resident ID " + residentId + " not found!"));
        
        // Attach the resident to the document request
        request.setResident(resident);
        
        // Save the document to the database
        return documentRepository.save(request);
    }

    // 2. Get all pending documents (for the Barangay Captain to review)
    public List getPendingRequests() {
        return documentRepository.findByStatus("Pending");
    }
}