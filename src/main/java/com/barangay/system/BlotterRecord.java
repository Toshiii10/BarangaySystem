package com.barangay.system;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "blotter_records")
public class BlotterRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String incidentType; // e.g., "Noise Complaint", "Theft", "Property Dispute"
    private String narrative;    // The detailed description of the event
    private LocalDate incidentDate;
    private String status;       // e.g., "Active", "Settled", "Forwarded to Police"
    private String respondentName; // Typed manually, as they might not be a resident

    // Link back to the Resident who is filing the complaint
    @ManyToOne
    @JoinColumn(name = "complainant_id", nullable = false)
    private Resident complainant;

    public BlotterRecord() {
        this.status = "Active"; // Default status when a new blotter is filed
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIncidentType() { return incidentType; }
    public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

    public String getNarrative() { return narrative; }
    public void setNarrative(String narrative) { this.narrative = narrative; }

    public LocalDate getIncidentDate() { return incidentDate; }
    public void setIncidentDate(LocalDate incidentDate) { this.incidentDate = incidentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRespondentName() { return respondentName; }
    public void setRespondentName(String respondentName) { this.respondentName = respondentName; }

    public Resident getComplainant() { return complainant; }
    public void setComplainant(Resident complainant) { this.complainant = complainant; }
}