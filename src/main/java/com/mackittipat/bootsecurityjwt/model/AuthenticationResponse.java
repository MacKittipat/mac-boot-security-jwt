package com.mackittipat.bootsecurityjwt.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse extends ApiResponse {

    private String token;

}
