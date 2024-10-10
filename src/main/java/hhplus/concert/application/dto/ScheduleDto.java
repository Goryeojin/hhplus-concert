package hhplus.concert.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDto {

    @Getter
    @Builder
    public static class Response {
        private Long concertId;
        private List<ScheduleInfo> schedule;

        @Getter
        @Builder
        public static class ScheduleInfo {
            private Long scheduleId;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime concertAt;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime reservationAt;
        }
    }
}
