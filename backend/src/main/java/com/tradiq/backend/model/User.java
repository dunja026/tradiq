package com.tradiq.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name="password_hash", nullable = false)
    private String passwordHash;

    @Column(name="cash_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal cashBalance;

    // DB-managed (DEFAULT CURRENT_TIMESTAMP)
    @Column(name="created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // DB-managed (DEFAULT 1) but updatable
    @Column(name="is_active", nullable = false, insertable = false)
    private boolean isActive;

    // Relationships
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Position> positions;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    // *** Constructors

    protected User() {
        // JPA only
    }

    private User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.cashBalance = new BigDecimal("10000.00");
    }

    // User factory
    public static User register(String email, String passwordHash) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (passwordHash == null) throw new IllegalArgumentException("Password hash is required");
        return new User(email, passwordHash);
    }

    // ***


    // Getters / setters
    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    // *** Domain methods

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        this.cashBalance = this.cashBalance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (amount.compareTo(this.cashBalance) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.cashBalance = this.cashBalance.subtract(amount);
    }

    public void deactivate() {
        if (!Boolean.TRUE.equals(isActive)) return;
        isActive = false;
    }

    // ***
}
