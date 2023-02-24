package ru.practicum.mapper;

import lombok.NoArgsConstructor;
import ru.practicum.hit.HitDto;
import ru.practicum.model.Hit;

@NoArgsConstructor
public class HitMapper {
    public static HitDto toHitDto(Hit hit) {
        return new HitDto(
                hit.getId(),
                hit.getApp().getName(),
                hit.getUri(),
                hit.getIp(),
                hit.getDate_time()
        );
    }

    public static Hit toHit(HitDto hitDto) {
        return new Hit(
                hitDto.getId(),
                null,
                hitDto.getUri(),
                hitDto.getIp(),
                hitDto.getTimestamp()
        );
    }
}
