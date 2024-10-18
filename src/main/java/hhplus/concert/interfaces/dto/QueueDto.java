package hhplus.concert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.concert.support.type.QueueStatus;
import lombok.Builder;

import java.time.LocalDateTime;

public class QueueDto {
    @Builder
    public record QueueRequest (
            Long userId
    ) {
    }

    @Builder
    public record QueueResponse (
        String token,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime enteredAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime expiredAt,
        QueueStatus status,
        Long rank
    ) {
    }
}
