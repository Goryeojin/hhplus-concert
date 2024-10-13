package hhplus.concert.interfaces.controller;

import hhplus.concert.interfaces.dto.PaymentDto;
import hhplus.concert.support.type.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {


    /**
     * 결제를 진행한다.
     * @param token 발급받은 토큰
     * @param request userId, reservationId
     * @return 결제 결과 dto
     */
    @PostMapping("/payments")
    public ResponseEntity<PaymentDto.Response> proceedPayment(
            @RequestHeader("Token") String token,
            @RequestBody PaymentDto.Request request
    ) {
        return ResponseEntity.ok(
                PaymentDto.Response.builder()
                        .paymentId(1L)
                        .amount(30000L)
                        .paymentStatus(PaymentStatus.COMPLETED).build()
        );
    }
}
