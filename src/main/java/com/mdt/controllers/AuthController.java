package com.mdt.controllers;

import com.mdt.dto.LoginResponse;
import com.mdt.entities.Role;
import com.mdt.entities.User;
import com.mdt.exception.InvalidCredentialsException;
import com.mdt.jwt.JwtService;
import com.mdt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Kullanıcı zaten var!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return "Kayıt başarılı!";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User loginRequest) {
        try {
            User dbUser = findUserByUsername(loginRequest.getUsername());
            validatePassword(loginRequest.getPassword(), dbUser.getPassword());

            String token = jwtService.generateToken(dbUser);
            LoginResponse response = new LoginResponse(token, "Giriş başarılı!");

            return ResponseEntity.ok(response);

        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, "Sunucu hatası: " + e.getMessage()));
        }
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Kullanıcı bulunamadı!"));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException("Hatalı şifre!");
        }
    }
}
