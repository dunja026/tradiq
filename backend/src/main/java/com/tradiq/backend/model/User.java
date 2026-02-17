package com.tradiq.backend.model;

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

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "user")
    private List<Position> positions;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
}
