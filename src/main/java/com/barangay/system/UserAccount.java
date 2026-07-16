package com.barangay.system;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Links this login account to a specific resident in the masterlist
    // It is nullable because an ADMIN might not be a registered resident
    @OneToOne
    @JoinColumn(name = "resident_id", referencedColumnName = "id", nullable = true)
    private Resident resident;

    // Constructors
    public UserAccount() {}

    public UserAccount(String username, String password, Role role, Resident resident) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.resident = resident;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Resident getResident() { return resident; }
    public void setResident(Resident resident) { this.resident = resident; }
}