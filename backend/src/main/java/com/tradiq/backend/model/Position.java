package com.tradiq.backend.model;

import jakarta.persistence.*;

@Entity
@Table(
        name="positions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "symbol"})
)
public class Position {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="position_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "user_id", nullable=false)
    private User user;

    @Column(nullable = false, length = 10)
    private String symbol;

    @Column(nullable = false)
    private Integer shares;

    // *** Constructors

    protected Position() {}

    private Position(User user, String symbol, Integer shares) {
        this.user = user;
        this.symbol = symbol;
        this.shares = shares;
    }

    // ***

    // Position Factory

    public Position open(User user, String symbol, Integer shares) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (symbol == null || symbol.isBlank()) throw new IllegalArgumentException("Symbol is required");
        if (shares == null || shares <= 0) throw new IllegalArgumentException("Shares must be positive");
        return new Position(user, symbol.toUpperCase(), shares);
    }

    // *** Domain methods

    public void addShares(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.shares += amount;
    }

    public void removeShares(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (this.shares < amount) throw new IllegalArgumentException("Not enough shares");
        this.shares -= amount;
    }

    // ***

    // *** Getters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getShares() {
        return shares;
    }
}
