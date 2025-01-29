package kr.hhplus.be.server.api.user.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.hhplus.be.server.api.user.domain.service.UserService;
import kr.hhplus.be.server.api.user.presentation.dto.UserRequest;
import kr.hhplus.be.server.api.user.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 잔액 조회")
    @GetMapping("/{userId}/user")
    public ResponseEntity<UserResponse> getUserAmount(
            @Parameter(description = "사용자ID") @PathVariable Long userId,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token
    ){
        return ResponseEntity.ok(UserResponse.toDto(userService.getUser(userId)));
    }

    @Operation(summary = "사용자 잔액 충전")
    @PostMapping("/{userId}/charge")
    public ResponseEntity<UserResponse> changeUserAmount(
            @PathVariable Long userId,
            @RequestBody @Valid UserRequest userRequest,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token

    ){
        return ResponseEntity.ok(UserResponse.toDto(userService.chargeAmount(userId, userRequest.getUserAmount())));
    }
}
