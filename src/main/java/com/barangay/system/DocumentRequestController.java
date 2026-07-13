package com.barangay.system;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents") // Base URL for documents
public class DocumentRequestController {

    private final DocumentRequestService documentService;

    // Constructor Injection
    public DocumentRequestController(DocumentRequestService documentService) {
        this.documentService = documentService;
    }

    // POST request to http://localhost:8080/api/documents/request/1
    @PostMapping("/request/{residentId}")
    public DocumentRequest requestDocument(@PathVariable Long residentId, @RequestBody DocumentRequest request) {
        return documentService.createRequest(residentId, request);
    }

    // GET request to http://localhost:8080/api/documents/pending
    // Added <DocumentRequest> to fix the raw type warning
    @GetMapping("/pending")
    public List<DocumentRequest> getPendingRequests() {
        return documentService.getPendingRequests();
    }
}