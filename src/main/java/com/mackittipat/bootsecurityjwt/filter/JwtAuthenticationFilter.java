package com.mackittipat.bootsecurityjwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mackittipat.bootsecurityjwt.model.ApiResponse;
import com.mackittipat.bootsecurityjwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                if(JwtUtil.isTokenValid(token)) {
                    Claims claims = JwtUtil.decode(token);
                    log.info("Converted jwtToken to Claim. {}", claims.toString());

                    List<SimpleGrantedAuthority> simpleGrantedAuthorityList =
                            ((ArrayList<String>) claims.get("scope")).stream()
                                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    UserDetails userDetails = new User(
                            claims.getSubject(),
                            "",
                            simpleGrantedAuthorityList);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JwtException e) {
                log.info("Invalid JWT");

                ObjectMapper objectMapper = new ObjectMapper();

                ApiResponse apiResponse = new ApiResponse();
                apiResponse.setCode("Jwt");
                apiResponse.setMessage("JWT is invalid");

                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                objectMapper.writeValue(response.getWriter(), apiResponse);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
