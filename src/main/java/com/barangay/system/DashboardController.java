package com.barangay.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ResidentRepository residentRepo;
    private final DocumentRequestRepository documentRepo;
    private final BlotterRecordRepository blotterRepo;

    // Constructor Injection (Keeping the code warning-free)
    public DashboardController(ResidentRepository residentRepo, 
                               DocumentRequestRepository documentRepo, 
                               BlotterRecordRepository blotterRepo) {
        this.residentRepo = residentRepo;
        this.documentRepo = documentRepo;
        this.blotterRepo = blotterRepo;
    }

    @GetMapping("/stats")
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        
        // Fetch the counts from the database
        stats.put("totalResidents", residentRepo.count());
        stats.put("pendingDocuments", documentRepo.countByStatus("Pending"));
        stats.put("activeBlotters", blotterRepo.countByStatus("Active"));
        
        return stats;
    }
}