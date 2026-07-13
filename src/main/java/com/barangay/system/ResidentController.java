package com.barangay.system;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;

    // Constructor Injection
    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    // CREATE (With Validation)
    @PostMapping
    public Resident addResident(@Valid @RequestBody Resident resident) {
        return residentService.addResident(resident);
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

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build(); 
    }
}