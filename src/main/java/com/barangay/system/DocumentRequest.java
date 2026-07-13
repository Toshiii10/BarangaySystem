package com.barangay.system;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "document_requests")
public class DocumentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentType; // e.g., "Barangay Clearance", "Certificate of Indigency"
    private String purpose;      // e.g., "Employment", "Financial Assistance"
    private String status;       // e.g., "Pending", "Approved", "Released"
    private LocalDate requestDate;

    // This is the relational magic. It creates a 'resident_id' column in your database.
    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    public DocumentRequest() {
        this.requestDate = LocalDate.now(); // Automatically sets today's date when created
        this.status = "Pending";            // Default status
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    public Resident getResident() { return resident; }
    public void setResident(Resident resident) { this.resident = resident; }
}