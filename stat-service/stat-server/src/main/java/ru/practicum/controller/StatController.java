package ru.practicum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.hit.HitDto;
import ru.practicum.service.StatService;
import ru.practicum.stats.Stats;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class StatController {
    private final StatService statService;
    private final static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;

    }

    @PostMapping("/hit")
    public ResponseEntity<HitDto> create(@Valid @RequestBody HitDto hitDto) {
        return new ResponseEntity<>(statService.create(hitDto), CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<Stats>> getStats(@RequestParam @DateTimeFormat(pattern = dateTimeFormat) LocalDateTime start,
                                                @RequestParam @DateTimeFormat(pattern = dateTimeFormat) LocalDateTime end,
                                                @RequestParam List<String> uris,
                                                @RequestParam(defaultValue = "false") boolean unique) {
        return new ResponseEntity<>(statService.getStats(start, end, uris, unique), OK);
    }
}
