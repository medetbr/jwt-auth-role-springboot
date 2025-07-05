package com.mdt.controllers;

import com.mdt.entities.Role;
import com.mdt.entities.User;
import com.mdt.jwt.JwtService;
import com.mdt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public String login(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            String token = jwtService.generateToken(dbUser);
            return token;
        } else {
            return "Hatalı şifre!";
        }
    }
}
