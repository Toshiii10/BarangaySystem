package com.barangay.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentRequestRepository documentRepository;
    private final ResidentRepository residentRepository;

    public DocumentController(DocumentRequestRepository documentRepository, ResidentRepository residentRepository) {
        this.documentRepository = documentRepository;
        this.residentRepository = residentRepository;
    }

    @PostMapping("/request/{residentId}")
    public ResponseEntity<?> requestDocument(@PathVariable Long residentId, @RequestBody DocumentRequest request) {
        
        // 1. Verify Resident exists
        Resident resident = residentRepository.findById(residentId).orElse(null);
        if (resident == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found.");

        // 2. --- ANTI-DUPLICATION GUARD ---
        // Check if they already have a "PENDING" request for this exact document type
        if (documentRepository.existsByResidentIdAndDocumentTypeAndStatus(residentId, request.getDocumentType(), "PENDING")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error: You already have a pending request for this document. Please wait for the admin to process it or complete your payment.");
        }

        // 3. Set the defaults and save
        request.setResident(resident);
        request.setStatus("PENDING");
        request.setAmountDue(100.00); // Set the standard P100 fee
        request.setRequestDate(LocalDateTime.now());
        
        DocumentRequest savedRequest = documentRepository.save(request);
        return ResponseEntity.ok(savedRequest);
    }

    // Process Payment and Update Status
    @PutMapping("/{requestId}/pay")
    public ResponseEntity<?> payForDocument(@PathVariable Long requestId) {
        java.util.Optional<DocumentRequest> optRequest = documentRepository.findById(requestId);
        
        if (optRequest.isPresent()) {
            DocumentRequest request = optRequest.get();
            request.setStatus("PAID - PENDING REVIEW");
            documentRepository.save(request);
            return ResponseEntity.ok(request);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document request not found.");
    }
}