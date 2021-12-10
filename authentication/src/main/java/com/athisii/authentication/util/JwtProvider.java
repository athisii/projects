package com.athisii.authentication.util;

import com.athisii.authentication.enums.TokenType;
import com.athisii.authentication.model.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.athisii.authentication.constant.ApplicationConstant.*;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.token.validity}")
    private Long accessTokenValidity;

    @Value("${jwt.refresh.token.validity}")
    private Long refreshTokenValidity;


    public String generateTokenFromAppUser(AppUser appUser, TokenType type) {
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());

        return JWT.create()
                .withSubject(appUser.getUsername())
                .withIssuer(TOKEN_ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ((TokenType.ACCESS_TOKEN.equals(type) ? accessTokenValidity : refreshTokenValidity) * 24 * 60 * 60 * 1000)))
                .withClaim(PERMISSIONS, getPermissionsFromAppUser(appUser))
                .withClaim("id", appUser.getUser().getId())
                .withClaim("name", UtilityMethods.createFullName(appUser.getUser().getFirstName(), appUser.getUser().getLastName()))
                .sign(algorithm);
    }

    private List<Long> getPermissionsFromAppUser(AppUser appUser) {
        return appUser.getAuthorities()
                .stream()
                .map(grantedAuthority -> Long.valueOf(grantedAuthority.getAuthority()))
                .collect(Collectors.toList());
    }

    public void checkTokenValidity(String token) {
        JWTVerifier verifier = getJWTVerifier();
        Date expiration = verifier.verify(token).getExpiresAt();
        if (expiration.before(new Date())) {
            throw new JWTVerificationException("The Token has expired on " + expiration);
        }
    }

    public String generateTokenFromRefreshToken(String refreshToken) {
        checkTokenValidity(refreshToken);
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
        JWTVerifier verifier = getJWTVerifier();

        return JWT.create()
                .withSubject(getSubjectFromToken(refreshToken))
                .withIssuer(verifier.verify(refreshToken).getIssuer())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenValidity))
                .withClaim(PERMISSIONS, findListValueFromToken(refreshToken, PERMISSIONS))
                .withClaim("id", findLongValueFromToken(refreshToken, "id"))
                .withClaim("name", findStringValueFromToken(refreshToken, "name"))
                .sign(algorithm);
    }


    public String getSubjectFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    public String findStringValueFromToken(String token, String name) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(name).asString();
    }

    public Integer findIntValueFromToken(String token, String name) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(name).asInt();

    }

    public Long findLongValueFromToken(String token, String name) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(name).asLong();
    }


    public List<Long> findListValueFromToken(String token, String name) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(name).asList(Long.class);

    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(TOKEN_ISSUER).build();
        } catch (JWTVerificationException ignored) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

}
