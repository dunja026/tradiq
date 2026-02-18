package com.tradiq.backend.repository;

import com.tradiq.backend.model.Transaction;
import com.tradiq.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndSymbol(User user, String symbol);
}
