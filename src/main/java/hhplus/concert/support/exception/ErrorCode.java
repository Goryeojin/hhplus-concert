package hhplus.concert.support.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "토큰 인증에 실패하였습니다."),
    CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트 정보를 찾을 수 없습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트 일정을 찾을 수 없습니다."),
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "좌석 정보 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약 정보 찾을 수 없습니다."),
    BALANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "잔액 정보를 찾을 수 없습니다."),
    BEFORE_RESERVATION_AT(HttpStatus.BAD_REQUEST, "예약하기에는 이릅니다."),
    AFTER_DEADLINE(HttpStatus.BAD_REQUEST, "예약 가능 시간이 지났습니다."),
    SEAT_UNAVAILABLE(HttpStatus.BAD_REQUEST, "예약 가능한 좌석이 아닙니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}