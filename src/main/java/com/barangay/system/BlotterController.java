package com.barangay.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blotter")
public class BlotterController {

    @Autowired
    private BlotterService blotterService;

    // POST: File a new blotter (e.g., /api/blotter/file/1)
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
    // Note: We use @RequestParam here to pass the status in the URL like: /api/blotter/update/1?status=Settled
    @PutMapping("/update/{blotterId}")
    public BlotterRecord updateStatus(@PathVariable Long blotterId, @RequestParam String status) {
        return blotterService.updateStatus(blotterId, status);
    }
}