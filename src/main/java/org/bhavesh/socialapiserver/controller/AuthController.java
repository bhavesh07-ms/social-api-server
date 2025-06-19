package org.bhavesh.socialapiserver.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bhavesh.socialapiserver.dto.ApiResponse;
import org.bhavesh.socialapiserver.dto.LoginRequest;
import org.bhavesh.socialapiserver.dto.LoginResponse;
import org.bhavesh.socialapiserver.dto.SignupRequest;
import org.bhavesh.socialapiserver.model.User;
import org.bhavesh.socialapiserver.service.AuthService;
import org.bhavesh.socialapiserver.storage.UserStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Map<String, String>>> signup(@RequestBody SignupRequest request) {
        User user = authService.signup(request);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("storedUsername", UserStorage.users.get(user.getUsername()).getUsername());

        return ResponseEntity.ok(new ApiResponse<>("User created successfully", responseData));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest requestdto,
                                                            HttpServletResponse response) {
        LoginResponse loginresponse = authService.login(requestdto);
        Cookie cookie = new Cookie("refreshToken", loginresponse.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok((new ApiResponse<>("logged in", loginresponse)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
        LoginResponse loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
