package kr.hhplus.be.server.api.user.presentation.dto;

import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.api.reservation.presentation.dto.ReservationResponseDto;
import kr.hhplus.be.server.api.user.domain.entity.User;
import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
public class UserResponseDto {

    private Long userId;
    private String userMail;
    private Integer userAmount;


    public static UserResponseDto toDto (User user) {
        return UserResponseDto.builder()
                .userId(user.getId())
                .userMail(user.getUserMail())
                .userAmount(user.getUserAmount())
                .build();
    }

    public static List<UserResponseDto> toDtoList (List<User> users) {
        List<UserResponseDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(UserResponseDto.toDto(user));
        }

        return dtos;
    }

}
