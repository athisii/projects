package com.athisii.authorization.filter;

import com.athisii.authorization.model.IdentityContext;
import com.athisii.authorization.model.ResponseDTO;
import com.athisii.authorization.util.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.athisii.authorization.constant.ApplicationConstant.PERMISSIONS;
import static com.athisii.authorization.constant.ApplicationConstant.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author athisii
 * @version 1.0
 * @since 13/10/21
 */

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private IdentityContext identityContext;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("### Authorization Filter Executing");
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Token not starting with 'Bearer ' or token is missing");
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(BAD_REQUEST.value());
            var responseDTO = new ResponseDTO<>(false, "Either token not starting with 'Bearer ' or token is missing", "Refresh token is missing in the header");
            objectMapper.writeValue(response.getOutputStream(), responseDTO);
        } else {
            //removes Bearer and  whitespace from authorization
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            try {
                jwtProvider.checkTokenValidity(token);
                identityContext.setAvailableStatus(true);
                identityContext.setId(jwtProvider.findLongValueFromToken(token, "id"));
                identityContext.setUserId(jwtProvider.getSubjectFromToken(token));
                identityContext.setPermissions(jwtProvider.findListValueFromToken(token, PERMISSIONS));
                identityContext.setFullName(jwtProvider.findStringValueFromToken(token, "name"));

                log.info("Authorization Job Finish");
                filterChain.doFilter(request, response);

            } catch (Exception ex) {
                log.error("Invalid Token.");
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setStatus(UNAUTHORIZED.value());
                var responseDTO = new ResponseDTO<>(false, "Invalid token.", "Some error occurred while parsing token.");
                objectMapper.writeValue(response.getOutputStream(), responseDTO);

            }

        }
    }
}
