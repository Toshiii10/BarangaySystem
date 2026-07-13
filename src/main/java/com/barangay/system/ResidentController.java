package com.barangay.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/residents") // Every route in this file starts with this path
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    // 1. CREATE: Add a new resident
    // POST request to http://localhost:8080/api/residents
    @PostMapping
    public Resident addResident(@RequestBody Resident resident) {
        return residentService.addResident(resident);
    }

    // 2. READ: Get all residents
    // GET request to http://localhost:8080/api/residents
    @GetMapping
    public List<Resident> getAllResidents() {
        return residentService.getAllResidents();
    }

    // 3. READ: Get a specific resident by ID
    // GET request to http://localhost:8080/api/residents/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        Optional<Resident> resident = residentService.getResidentById(id);
        
        // If the resident exists, return them with a 200 OK status. 
        // If not, return a 404 Not Found status.
        return resident.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. READ: Get residents by zone (Purok)
    // GET request to http://localhost:8080/api/residents/zone/{zone}
    @GetMapping("/zone/{zone}")
    public List<Resident> getResidentsByZone(@PathVariable String zone) {
        return residentService.getResidentsByZone(zone);
    }

    // 5. DELETE: Remove a resident
    // DELETE request to http://localhost:8080/api/residents/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build(); // Returns a 204 No Content status
    }
}