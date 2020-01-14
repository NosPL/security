package com.example.security.security_config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@NoArgsConstructor(access = AccessLevel.NONE)
public class JwtUtils {
    public static String getClaim(String jwt, String secretKey, String claim) throws JwtUtilsException {
        DecodedJWT decodedJWT = getJwt(jwt, secretKey);
        return decodedJWT.getClaim(claim).asString();
    }

    public static DecodedJWT getJwt(String token, String secretKey) throws JwtUtilsException {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT
                    .require(Algorithm.HMAC512(secretKey.getBytes()))
                    .build()
                    .verify(token);
            return decodedJWT;
        } catch (AlgorithmMismatchException e) {
            throw new JwtUtilsException("Jwt token has incorrect algorithm");
        } catch (SignatureVerificationException e) {
            throw new JwtUtilsException("Jwt token incorrect signature");
        } catch (TokenExpiredException e) {
            throw new JwtUtilsException("Jwt token has expired");
        } catch (InvalidClaimException e) {
            throw new JwtUtilsException("Jwt token has incorrect claims");
        } catch (JWTVerificationException e) {
            throw new JwtUtilsException("Token verification failed");
        }
    }

    public static String getSubject(String jwt, String secretKey) throws JwtUtilsException {
        return getJwt(jwt, secretKey).getSubject();
    }

    @ResponseStatus(BAD_REQUEST)
    public static class JwtUtilsException extends RuntimeException {
        public JwtUtilsException(String message) {
            super(message);
        }
    }
}
