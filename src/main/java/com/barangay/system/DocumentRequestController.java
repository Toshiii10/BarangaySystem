package com.barangay.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents") // Base URL for documents
public class DocumentRequestController {

    @Autowired
    private DocumentRequestService documentService;

    // POST request to http://localhost:8080/api/documents/request/1
    // The "1" at the end is the Resident ID
    @PostMapping("/request/{residentId}")
    public DocumentRequest requestDocument(@PathVariable Long residentId, @RequestBody DocumentRequest request) {
        return documentService.createRequest(residentId, request);
    }

    // GET request to http://localhost:8080/api/documents/pending
    @GetMapping("/pending")
    public List getPendingRequests() {
        return documentService.getPendingRequests();
    }
}