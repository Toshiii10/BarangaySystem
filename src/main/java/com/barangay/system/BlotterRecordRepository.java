package com.barangay.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlotterRecordRepository extends JpaRepository<BlotterRecord, Long> {
    
    // Custom search to find all cases that are currently "Active"
    List<BlotterRecord> findByStatus(String status);
}