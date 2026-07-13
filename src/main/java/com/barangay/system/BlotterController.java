package com.barangay.system;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blotter")
public class BlotterController {

    private final BlotterService blotterService;

    // Constructor Injection
    public BlotterController(BlotterService blotterService) {
        this.blotterService = blotterService;
    }

    // POST: File a new blotter
    @PostMapping("/file/{complainantId}")
    public BlotterRecord fileBlotter(@PathVariable Long complainantId, @RequestBody BlotterRecord record) {
        return blotterService.fileBlotter(complainantId, record);
    }

    // GET: View all blotter records
    @GetMapping
    public List<BlotterRecord> getAllRecords() {
        return blotterService.getAllRecords();
    }

    // PUT: Update the status of an existing case
    @PutMapping("/update/{blotterId}")
    public BlotterRecord updateStatus(@PathVariable Long blotterId, @RequestParam String status) {
        return blotterService.updateStatus(blotterId, status);
    }
}