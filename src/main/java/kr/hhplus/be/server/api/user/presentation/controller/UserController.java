package kr.hhplus.be.server.api.user.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.hhplus.be.server.api.user.application.service.UserService;
import kr.hhplus.be.server.api.user.presentation.dto.UserRequestDto;
import kr.hhplus.be.server.api.user.presentation.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "사용자 잔액 조회")
    @GetMapping("/{userId}/user")
    public ResponseEntity<UserResponseDto> getUserAmount(
            @Parameter(description = "사용자ID") @PathVariable Long userId,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token
    ){
        return ResponseEntity.ok(UserResponseDto.toDto(userService.getUserAmount(userId)));
    }

    @Operation(summary = "사용자 잔액 충전")
    @PostMapping("/{userId}/charge")
    public ResponseEntity<UserResponseDto> changeUserAmount(
            @PathVariable Long userId,
            @RequestBody @Valid UserRequestDto userRequestDto,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token

    ){
        return ResponseEntity.ok(UserResponseDto.toDto(userService.chargeAmount(userId, userRequestDto)));
    }
}
