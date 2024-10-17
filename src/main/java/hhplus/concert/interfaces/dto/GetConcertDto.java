package hhplus.concert.interfaces.dto;

import lombok.Builder;

import java.util.List;

public class GetConcertDto {

    @Builder
    public record Response (
            List<ConcertDto> concerts
    ) {
    }
}
