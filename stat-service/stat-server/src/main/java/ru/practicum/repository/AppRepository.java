package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.App;
import ru.practicum.model.Hit;
import ru.practicum.stats.Stats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {
    Optional<App> findByName(String name);
}
