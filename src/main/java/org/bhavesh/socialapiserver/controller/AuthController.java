package org.bhavesh.socialapiserver.controller;


import lombok.RequiredArgsConstructor;
import org.bhavesh.socialapiserver.dto.ApiResponse;
import org.bhavesh.socialapiserver.dto.LoginRequest;
import org.bhavesh.socialapiserver.dto.SignupRequest;
import org.bhavesh.socialapiserver.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok(new ApiResponse<>("Signup successful", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        LoginResponse loginResponse = new LoginResponse(token, request.getUsername());
        return ResponseEntity.ok(new ApiResponse<>("Login successful", loginResponse));
