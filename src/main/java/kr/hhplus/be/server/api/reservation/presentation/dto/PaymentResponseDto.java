package kr.hhplus.be.server.api.reservation.presentation.dto;

public class PaymentResponseDto {

    private String status;
    private String message;

    public PaymentResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }


}
