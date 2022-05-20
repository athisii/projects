package com.athisii.authentication.controller;

import com.athisii.authentication.dto.LoginSuccessDto;
import com.athisii.authentication.util.JwtProvider;
import com.athisii.base.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.athisii.authentication.constant.ApplicationConstant.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

@Slf4j
@RestController
public class TokenController {
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/refresh_token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Inside refreshToken()");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(APPLICATION_JSON_VALUE);
        ResponseDTO responseDTO;

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Token not starting with 'Bearer ' or token is missing");
            response.setStatus(BAD_REQUEST.value());
            responseDTO = new ResponseDTO<>(false, "Either token not starting with 'Bearer ' or token is missing", "Refresh token is missing in the header");
        } else {
            String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
            try {
                String accessToken = jwtProvider.generateTokenFromRefreshToken(refreshToken);
                LoginSuccessDto tokenResponse = new LoginSuccessDto(accessToken, refreshToken);
                response.setStatus(OK.value());
                responseDTO = new ResponseDTO<>(true, "Token fetched successfully", tokenResponse);
            } catch (Exception ex) {
                log.error("Invalid Token.");
                response.setStatus(UNAUTHORIZED.value());
                responseDTO = new ResponseDTO<>(false, "Invalid token.", "Some error occurred while parsing token.");
            }
        }
        objectMapper.writeValue(response.getOutputStream(), responseDTO);
    }
}
