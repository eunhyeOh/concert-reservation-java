package kr.hhplus.be.server.api.user.presentation.dto;

import kr.hhplus.be.server.api.user.domain.dto.UserResult;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class UserResponse {

    private Long userId;
    private String userMail;
    private Integer userAmount;


    public static UserResponse toDto (UserResult user) {
        return UserResponse.builder()
                .userId(user.userId())
                .userMail(user.userMail())
                .userAmount(user.userAmount())
                .build();
    }

    public static List<UserResponse> toDtoList (List<UserResult> users) {
        List<UserResponse> resultList = new ArrayList<>();
        for (UserResult user : users) {
            resultList.add(UserResponse.toDto(user));
        }

        return resultList;
    }

}
