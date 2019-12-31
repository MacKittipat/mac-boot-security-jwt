package com.mackittipat.bootsecurityjwt.controller;

import com.mackittipat.bootsecurityjwt.model.AuthenticationRequest;
import com.mackittipat.bootsecurityjwt.model.AuthenticationResponse;
import com.mackittipat.bootsecurityjwt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("authenticate")
    public AuthenticationResponse authenticate(@RequestBody(required = false) AuthenticationRequest authenticationRequest) {
        String token = "";

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest
                            .getPassword()));

            token = JwtUtil.generateToken(authentication);
        } catch (BadCredentialsException e) {
            log.info("User {} try to login with incorrect password", authenticationRequest.getUsername());
        }
        return AuthenticationResponse.builder().token(token).build();
    }

}
