package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.stats.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("select a.name, h.uri, count (h) from Hit h join App a on a.id=h.app.id " +
            "where h.date_time between :startDateTime and :endDateTime " +
            "group by  h.uri,a.name order by count(h) desc")
    List<Stats> findAllHits(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select a.name, h.uri, count (h.ip) from Hit h join App a on a.id=h.app.id " +
            "where h.date_time between :startDateTime and :endDateTime " +
            "group by h.uri,a.name order by count(h.ip) desc")
    List<Stats> findAllUniqueHits(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select a.name, h.uri, count (h) from Hit h join App a on a.id=h.app.id " +
            "where (h.date_time between :startDateTime and :endDateTime) and (h.uri in :uris)" +
            "group by h.uri,a.name order by count(h) desc")
    List<Stats> findHitsByUris(LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> uris);

    @Query("select a.name, h.uri, count (h.ip) from Hit h join App a on a.id=h.app.id " +
            "where (h.date_time between :startDateTime and :endDateTime) and (h.uri in :uris)" +
            "group by h.uri,a.name order by count(h.ip) desc")
    List<Stats> findUniqueHitsByUris(LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> uris);
}
