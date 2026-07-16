package com.barangay.system;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DocumentRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    private String documentType; // e.g., "Barangay Clearance"
    private String purpose;
    private String status;       // e.g., "PENDING", "PAID", "APPROVED"
    private Double amountDue;    // e.g., 100.00
    private LocalDateTime requestDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Resident getResident() { return resident; }
    public void setResident(Resident resident) { this.resident = resident; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getAmountDue() { return amountDue; }
    public void setAmountDue(Double amountDue) { this.amountDue = amountDue; }

    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
}