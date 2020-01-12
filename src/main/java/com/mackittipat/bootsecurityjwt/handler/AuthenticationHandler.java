package com.mackittipat.bootsecurityjwt.handler;

import com.mackittipat.bootsecurityjwt.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AuthenticationHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationFail() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode("BadCredentials");
        apiResponse.setMessage("Login failed. Bad credentials");
        return new ResponseEntity<>(apiResponse, null, HttpStatus.FORBIDDEN);
    }

}
