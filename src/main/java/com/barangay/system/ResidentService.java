package com.barangay.system;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {

    private final ResidentRepository repository;

    // Constructor Injection
    public ResidentService(ResidentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public Resident addResident(Resident resident) {
        return repository.save(resident); 
    }

    // READ (All)
    public List<Resident> getAllResidents() {
        return repository.findAll(); 
    }

    // READ (By ID)
    public Optional<Resident> getResidentById(Long id) {
        return repository.findById(id); 
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