package com.tradiq.backend.repository;

import com.tradiq.backend.model.Position;
import com.tradiq.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByUser(User user);
    Optional<Position> findByUserAndSymbol(User user, String symbol);
}
