package com.barangay.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;
    private final ResidentRepository residentRepository; // Added the missing repository

    // Constructor Injection updated to include ResidentRepository
    public ResidentController(ResidentService residentService, ResidentRepository residentRepository) {
        this.residentService = residentService;
        this.residentRepository = residentRepository;
    }

    // CREATE (With Validation)
    @PostMapping
    public ResponseEntity<?> addResident(@RequestBody Resident resident) {
        
        // --- ANTI-DUPLICATION GUARD ---
        if (residentRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(resident.getFirstName(), resident.getLastName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error: A resident with this first and last name already exists in the system.");
        }
        // ------------------------------

        Resident savedResident = residentRepository.save(resident);
        return ResponseEntity.ok(savedResident);
    }

    // READ: All
    @GetMapping
    public List<Resident> getAllResidents() {
        return residentService.getAllResidents();
    }

    // READ: By ID
    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        Optional<Resident> resident = residentService.getResidentById(id);
        return resident.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ: By Zone
    @GetMapping("/zone/{zone}")
    public List<Resident> getResidentsByZone(@PathVariable String zone) {
        return residentService.getResidentsByZone(zone);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateResident(@PathVariable Long id, @RequestBody Resident updatedData) {
        return residentService.getResidentById(id).map(existingResident -> {
            
            // Note: If you want to prevent duplicate names on EDIT, you would add the guard here too!
            
            existingResident.setFirstName(updatedData.getFirstName());
            existingResident.setLastName(updatedData.getLastName());
            existingResident.setZone(updatedData.getZone());
            existingResident.setCivilStatus(updatedData.getCivilStatus());
            
            Resident saved = residentRepository.save(existingResident);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

   // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResident(@PathVariable Long id) {
        try {
            residentService.deleteResident(id);
            return ResponseEntity.noContent().build(); 
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // This catches the exact error when a resident has attached documents/blotters
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cannot delete resident: This person has existing document requests or blotter records attached to their name.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while deleting the resident.");
        }
    }
}
