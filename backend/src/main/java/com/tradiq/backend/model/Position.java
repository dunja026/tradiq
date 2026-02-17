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
}
