package com.barangay.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlotterService {

    @Autowired
    private BlotterRecordRepository blotterRepository;

    @Autowired
    private ResidentRepository residentRepository;

    // File a new blotter record
    public BlotterRecord fileBlotter(Long complainantId, BlotterRecord record) {
        Resident complainant = residentRepository.findById(complainantId)
            .orElseThrow(() -> new RuntimeException("Error: Complainant ID not found."));
            
        record.setComplainant(complainant);
        return blotterRepository.save(record);
    }

    // Get all blotter records
    public List<BlotterRecord> getAllRecords() {
        return blotterRepository.findAll();
    }

    // Update the status (e.g., from "Active" to "Settled")
    public BlotterRecord updateStatus(Long blotterId, String newStatus) {
        BlotterRecord record = blotterRepository.findById(blotterId)
            .orElseThrow(() -> new RuntimeException("Error: Blotter Record not found."));
            
        record.setStatus(newStatus);
        return blotterRepository.save(record);
    }
}