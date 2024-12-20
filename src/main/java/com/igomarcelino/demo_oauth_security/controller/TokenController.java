package com.igomarcelino.demo_oauth_security.controller;

import com.igomarcelino.demo_oauth_security.dto.LoginRequestDTO;
import com.igomarcelino.demo_oauth_security.dto.LoginResponseDTO;
import com.igomarcelino.demo_oauth_security.repository.UserRepository;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestController
public class TokenController {

    @Autowired
    JwtEncoder jwtEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;


    @PostMapping("/login")

    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        var user =userRepository.findByUserLogin(loginRequestDTO.username());

        // verificar se o usuario existe e se a senha confere com a armazenada no banco
        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequestDTO, cryptPasswordEncoder)){
            throw new BadCredentialsException("Verify user credential");
        }

        // definir o tempo de expiracao do token
        var now = Instant.now();
        var expiresIn = 300l;

        var claims = JwtClaimsSet.builder().
                issuer("backendDemo-Spring").
                subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresIn)).
                issuedAt(now).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue,expiresIn));
    }
}
