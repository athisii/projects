package com.athisii.authorization.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.athisii.authorization.constant.ApplicationConstant.TOKEN_CANNOT_BE_VERIFIED;
import static com.athisii.authorization.constant.ApplicationConstant.TOKEN_ISSUER;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;


    public void checkTokenValidity(String token) {
        JWTVerifier verifier = getJWTVerifier();
        Date expiration = verifier.verify(token).getExpiresAt();
        if (expiration.before(new Date())) {
            throw new JWTVerificationException("The Token has expired on " + expiration);
        }
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
