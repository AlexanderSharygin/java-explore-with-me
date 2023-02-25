package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.practicum.hit.HitDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.App;
import ru.practicum.model.Hit;
import ru.practicum.repository.AppRepository;
import ru.practicum.repository.HitRepository;
import ru.practicum.stats.Stats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StatService {

    private final HitRepository hitRepository;
    private final AppRepository appRepository;

    @Autowired
    public StatService(HitRepository hitRepository, AppRepository appRepository) {
        this.hitRepository = hitRepository;
        this.appRepository = appRepository;
    }

    public HitDto create(HitDto hitDto) {
        hitDto.setTimestamp(LocalDateTime.now());
        Optional<App> existedApp = appRepository.findByName(hitDto.getApp());
        App newApp;
        if (existedApp.isEmpty()) {
            App app = new App();
            app.setName(hitDto.getApp());
            newApp = appRepository.save(app);
            log.info("В таблицу APP добавлен новый элемент с именем {}", app.getName());
        } else {
            newApp = existedApp.get();
        }
        Hit hit = HitMapper.toHit(hitDto);
        hit.setApp(newApp);
        log.info("В таблицу HIT добавлен новый элемент - обращение к {} с IP {}", hit.getUri(), hit.getIp());

        return HitMapper.toHitDto(hitRepository.save(hit));
    }

    public List<Stats> getStats(LocalDateTime startRange, LocalDateTime endRange, List<String> uris, boolean unique) {
        List<Stats> stats;
        if (uris == null || uris.isEmpty()) {
            if (!unique) {
                stats = hitRepository.findAllHits(startRange, endRange);
            } else {
                stats = hitRepository.findAllUniqueHits(startRange, endRange);
            }
        } else {
            if (!unique) {
                stats = hitRepository.findHitsByUris(startRange, endRange, uris);
            } else {
                stats = hitRepository.findUniqueHitsByUris(startRange, endRange, uris);
            }
        }

        return stats;
    }
}
