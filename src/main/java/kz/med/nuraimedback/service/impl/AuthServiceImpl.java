package kz.med.nuraimedback.service.impl;

import kz.med.nuraimedback.exception.InvalidCredentialsException;
import kz.med.nuraimedback.exception.ResourceNotFoundException;
import kz.med.nuraimedback.model.Token;
import kz.med.nuraimedback.model.User;
import kz.med.nuraimedback.model.dto.AuthRequestDto;
import kz.med.nuraimedback.model.dto.AuthResponseDto;
import kz.med.nuraimedback.repository.TokenRepository;
import kz.med.nuraimedback.repository.UserRepository;
import kz.med.nuraimedback.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthResponseDto login(AuthRequestDto req) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        var user = userRepo.findByUsernameAndIsActive(req.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", req.getUsername()));

        return generateToken(user);
    }

    public AuthResponseDto refresh(String token) {
        var username = jwtUtil.extractUsername(token);

        var user = userRepo.findByUsernameAndIsActive(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", username));

        return generateToken(user);
    }

    public void logout(String token) {
        var existing = tokenRepo.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "value", token));

        existing.setRevoked(true);
        tokenRepo.save(existing);
    }

    private AuthResponseDto generateToken(User user) {
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        long expiration = jwtUtil.getAccessTokenExpirationMillis();

        tokenRepo.save(Token.builder()
                .token(accessToken)
                .expired(false)
                .revoked(false)
                .user(user)
                .build());

        tokenRepo.save(Token.builder()
                .token(refreshToken)
                .expired(false)
                .revoked(false)
                .user(user)
                .build());

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiration)
                .build();
    }
}

