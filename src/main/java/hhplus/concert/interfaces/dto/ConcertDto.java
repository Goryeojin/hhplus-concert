package hhplus.concert.interfaces.dto;

import hhplus.concert.support.type.ConcertStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "콘서트 조회에 대한 응답 DTO")
public record ConcertDto(
        Long concertId,
        String title,
        String description,
        ConcertStatus status
) {
}
