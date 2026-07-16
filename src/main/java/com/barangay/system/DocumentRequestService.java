package com.barangay.system;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentRequestService {

    private final DocumentRequestRepository documentRepository;
    private final ResidentRepository residentRepository;

    // Constructor Injection (This makes the VS Code warning go away)
    public DocumentRequestService(DocumentRequestRepository documentRepository, ResidentRepository residentRepository) {
        this.documentRepository = documentRepository;
        this.residentRepository = residentRepository;
    }

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

    // 2. Get all pending documents 
    // Added <DocumentRequest> to fix the raw type warning
    public List<DocumentRequest> getPendingRequests() {
        return documentRepository.findByStatus("Pending");
    }
}