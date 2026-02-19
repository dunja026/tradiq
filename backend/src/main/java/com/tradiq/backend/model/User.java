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

    // Getters / setters
    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    // TODO make domain method for deposit and withdrawing instead of setCashBalance
    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void deactivate() {
        if (!Boolean.TRUE.equals(isActive)) return;
        isActive = false;
    }
}
