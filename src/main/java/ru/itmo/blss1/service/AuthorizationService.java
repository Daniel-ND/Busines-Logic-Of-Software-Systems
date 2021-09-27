package ru.itmo.blss1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.blss1.config.jwt.JwtProvider;
import ru.itmo.blss1.data.dto.AuthorizationDTO;
import ru.itmo.blss1.data.repository.UsersRepository;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public AuthorizationDTO authorize(final AuthorizationDTO authorizationDto)
            throws Exception {
        final var authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authorizationDto.getUsername(), authorizationDto.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new Exception("Bad credentials");
        }
        final String token = jwtProvider.generateToken(authentication.getName());
        final var role = userRepository.findRoleByLogin(authentication.getName());
        //authorizationDto.setId(user.getId());
        authorizationDto.setToken(token);
        authorizationDto.setRole(role);
        //authorizationDto.setExpiresAt(jwtUtil.getExpirationDate(token).toInstant());
        return authorizationDto;
    }
}