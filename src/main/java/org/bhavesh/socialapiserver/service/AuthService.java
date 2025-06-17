package org.bhavesh.socialapiserver.service;



import lombok.RequiredArgsConstructor;
import org.bhavesh.socialapiserver.dto.LoginRequest;
import org.bhavesh.socialapiserver.dto.SignupRequest;
import org.bhavesh.socialapiserver.model.User;
import org.bhavesh.socialapiserver.storage.UserStorage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequest request) {
        if (UserStorage.users.containsKey(request.getUsername())) {
            throw new CustomException("Username already exists");
        }
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        UserStorage.users.put(user.getUsername(), user);
    }

    public String login(LoginRequest request) {
        User user = UserStorage.users.get(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid username or password");
        }
        return jwtUtil.generateToken(user.getUsername());
    }
}

