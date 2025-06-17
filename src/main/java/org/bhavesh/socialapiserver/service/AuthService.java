package org.bhavesh.socialapiserver.service;



import lombok.RequiredArgsConstructor;
import org.bhavesh.socialapiserver.dto.LoginRequest;
import org.bhavesh.socialapiserver.dto.LoginResponse;
import org.bhavesh.socialapiserver.dto.SignupRequest;
import org.bhavesh.socialapiserver.exception.UserException;
import org.bhavesh.socialapiserver.model.User;
import org.bhavesh.socialapiserver.security.JwtUtil;
import org.bhavesh.socialapiserver.storage.UserStorage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequest request) {
        if (UserStorage.users.containsKey(request.getUsername())) {
            throw new UserException("Username already exists");
        }

        User user = new User(UUID.randomUUID().toString(), request.getUsername(),
                passwordEncoder.encode(request.getPassword()));
        UserStorage.users.put(user.getUsername(), user);

    }


    public LoginResponse login(LoginRequest request) {
        User user = UserStorage.users.get(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("Invalid username or password");
        }

        String accessToken = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        return new LoginResponse(accessToken, refreshToken, user.getUsername());
    }

    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new UserException("Invalid or expired refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        User user = UserStorage.users.get(username); // your in-memory map

        if (user == null) {
            throw new UserException("User not found");
        }

        String newAccessToken = jwtUtil.generateToken(username);

        return new LoginResponse(newAccessToken, refreshToken, username);
    }


}

