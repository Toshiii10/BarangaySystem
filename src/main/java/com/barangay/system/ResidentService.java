package com.barangay.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {

    // Dependency Injection: Spring automatically provides the repository here
    @Autowired
    private ResidentRepository repository;

    // CREATE
    public Resident addResident(Resident resident) {
        return repository.save(resident); // Generates: INSERT INTO residents...
    }

    // READ (All)
    public List<Resident> getAllResidents() {
        return repository.findAll(); // Generates: SELECT * FROM residents
    }

    // READ (By ID)
    public Optional<Resident> getResidentById(Long id) {
        return repository.findById(id); // Generates: SELECT * FROM residents WHERE id = ?
    }
    
    // READ (By Zone)
    public List<Resident> getResidentsByZone(String zone) {
        return repository.findByZone(zone);
    }

    // DELETE
    public void deleteResident(Long id) {
        repository.deleteById(id);
    }
}