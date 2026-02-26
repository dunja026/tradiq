package com.tradiq.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 10)
    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal price;

    @Column(name="created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // *** Constructors

    protected Transaction() {}

    private Transaction(User user, String symbol, TransactionType type, int quantity, BigDecimal price) {
        this.user = user;
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    // ***

    // *** Transaction Factory

    public static Transaction record(User user, String symbol, TransactionType type, int quantity, BigDecimal price) {
        if (user == null) throw new IllegalArgumentException("User is required");
        if (symbol == null || symbol.isBlank()) throw new IllegalArgumentException("Symbol is required");
        if (type == null) throw new IllegalArgumentException("Type is required");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Price is required");
        return new Transaction(user, symbol, type, quantity, price);
    }

    // ***

    // *** Domain methods

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
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

    public TransactionType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ***
}
