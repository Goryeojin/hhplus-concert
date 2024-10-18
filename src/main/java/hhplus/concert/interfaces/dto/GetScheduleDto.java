package hhplus.concert.interfaces.dto;

import lombok.Builder;

import java.util.List;

public class GetScheduleDto {

    @Builder
    public record ScheduleResponse (
        Long concertId,
        List<ScheduleDto> schedules
    ) {
    }
}
