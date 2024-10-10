package hhplus.concert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.concert.support.type.QueueStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class QueueDto {

    @Getter
    @Builder
    public static class Response {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        private QueueStatus status;
        private Long remainingQueueCount;
    }
}
