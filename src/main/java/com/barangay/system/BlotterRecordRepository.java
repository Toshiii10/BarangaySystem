package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlotterRecordRepository extends JpaRepository<BlotterRecord, Long> {
    
    // Custom search to find all cases that are currently "Active"
    List<BlotterRecord> findByStatus(String status);
}