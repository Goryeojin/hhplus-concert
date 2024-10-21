package hhplus.concert.interfaces.dto;

import hhplus.concert.domain.model.Point;
import jakarta.validation.constraints.Min;
import lombok.Builder;

public class PointDto {

    public record PointRequest(
            @Min(value = 1, message = "Amount must be greater then zero.")
            Long amount
    ) {
    }

    @Builder
    public record PointResponse(
        Long userId,
        Long currentAmount
    ) {
    }

    public static PointResponse toResponse(Point point) {
        return PointResponse.builder()
                .userId(point.userId())
                .currentAmount(point.amount())
                .build();
    }
}
