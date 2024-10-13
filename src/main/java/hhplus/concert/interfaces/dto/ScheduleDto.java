package hhplus.concert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDto {

    @Builder
    public record Response (
        Long concertId,
        List<ScheduleInfo> schedules
    ) {
        @Builder
        public record ScheduleInfo (
            Long scheduleId,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime concertAt,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime reservationAt
        ) {
        }
    }
}
