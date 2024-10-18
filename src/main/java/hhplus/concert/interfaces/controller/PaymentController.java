package hhplus.concert.interfaces.controller;

import hhplus.concert.application.facade.PaymentFacade;
import hhplus.concert.domain.model.Payment;
import hhplus.concert.interfaces.dto.PaymentDto;
import hhplus.concert.support.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFacade paymentFacade;

    /**
     * 결제를 진행한다.
     * @param token 발급받은 토큰
     * @param request userId, reservationId
     * @return 결제 결과 dto
     */
    @PostMapping
    public ResponseEntity<PaymentDto.PaymentResponse> proceedPayment(
            @RequestHeader("Token") String token,
            @RequestBody PaymentDto.PaymentRequest request
    ) {
        Payment payment = paymentFacade.payment(token, request.reservationId(), request.userId());
        return ResponseEntity.ok(
                PaymentDto.PaymentResponse.builder()
                        .paymentId(payment.id())
                        .amount(payment.amount())
                        .paymentAt(payment.paymentAt())
                        .build()
        );
    }
}
