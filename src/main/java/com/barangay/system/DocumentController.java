package com.barangay.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List; // THIS FIXES THE LIST ERROR!

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentRequestRepository documentRepository;
    private final ResidentRepository residentRepository;

    public DocumentController(DocumentRequestRepository documentRepository, ResidentRepository residentRepository) {
        this.documentRepository = documentRepository;
        this.residentRepository = residentRepository;
    }

    // Resident Submits a Request
    @PostMapping("/request/{residentId}")
    public ResponseEntity<?> requestDocument(@PathVariable Long residentId, @RequestBody DocumentRequest request) {
        Resident resident = residentRepository.findById(residentId).orElse(null);
        if (resident == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found.");

        if (documentRepository.existsByResidentIdAndDocumentTypeAndStatus(residentId, request.getDocumentType(), "PENDING")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error: You already have a pending request. Please wait for processing or complete your payment.");
        }

        request.setResident(resident);
        request.setStatus("PENDING");
        request.setAmountDue(100.00); 
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

    // --- NEW ADMIN PIPELINE METHODS ---

    // Fetch all requests that are Paid and ready for Admin review
    @GetMapping("/paid")
    public ResponseEntity<List<DocumentRequest>> getPaidRequests() {
        List<DocumentRequest> paidRequests = documentRepository.findByStatus("PAID - PENDING REVIEW");
        return ResponseEntity.ok(paidRequests);
    }

    // Admin approves the document
    @PutMapping("/{requestId}/approve")
    public ResponseEntity<?> approveDocument(@PathVariable Long requestId) {
        java.util.Optional<DocumentRequest> optRequest = documentRepository.findById(requestId);
        
        if (optRequest.isPresent()) {
            DocumentRequest request = optRequest.get();
            request.setStatus("APPROVED");
            documentRepository.save(request);
            return ResponseEntity.ok(request);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document request not found.");
    }
}
