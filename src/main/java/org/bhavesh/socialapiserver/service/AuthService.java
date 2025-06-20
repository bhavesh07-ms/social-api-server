package org.bhavesh.socialapiserver.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    //put username,User in concurrent hashmap
    public User signup(SignupRequest request) {
        if (UserStorage.users.containsKey(request.getUsername())) {
            throw new UserException("Username already exists");
        }

        User user = new User(UUID.randomUUID().toString(), request.getUsername(),
                passwordEncoder.encode(request.getPassword()));
        UserStorage.users.put(user.getUsername(), user);
        log.info("userstorage: {}", UserStorage.users.get(user.getUsername()));
        return user;
    }


    public LoginResponse login(LoginRequest request) {
        User user = UserStorage.users.get(request.getUsername());
        // Check if user exists and password matches
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("Invalid username or password");
        }

        log.info("user info: {}", user.getUsername());

        String accessToken = jwtUtil.generateToken(user.getUsername());
        log.info("Generated access token");

        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        log.info("Generated refresh token");

        return new LoginResponse(accessToken, refreshToken, user.getUsername());
    }
    //gives new access token if refresh token is valid
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

