package com.athisii.authentication.filter;

import com.athisii.authentication.dto.LoginDto;
import com.athisii.authentication.dto.LoginSuccessDto;
import com.athisii.authentication.enums.TokenType;
import com.athisii.authentication.model.AppUser;
import com.athisii.authentication.util.JwtProvider;
import com.athisii.base.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */


@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //check if credentials provided is correct.
        log.info("Inside attemptAuthentication()");
        LoginDto usernamePassword = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usernamePassword.getUsername(), usernamePassword.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //logic to create token
        log.info("Inside successfulAuthentication()");
        AppUser user = (AppUser) authResult.getPrincipal();
        String accessToken = jwtProvider.generateTokenFromAppUser(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.generateTokenFromAppUser(user, TokenType.REFRESH_TOKEN);

        LoginSuccessDto tokenResponse = new LoginSuccessDto(accessToken, refreshToken);

        var responseDTO = new ResponseDTO<>(true, "Token fetched successfully", tokenResponse);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), responseDTO);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //logic to limit failure attempts
        log.info("Inside unsuccessfulAuthentication()");
        var responseDTO = new ResponseDTO<>(false, "Unauthorized", "Bad credentials");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), responseDTO);
    }
}
