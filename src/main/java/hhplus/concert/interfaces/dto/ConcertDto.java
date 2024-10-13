package hhplus.concert.interfaces.dto;

import lombok.Builder;

import java.util.List;

public class ConcertDto {

    @Builder
    public record Response (
            List<ConcertInfo> concerts
    ) {
        @Builder
        public record ConcertInfo (
                Long concertId,
                String title,
                String description
        ) {
        }
    }
}
