package com.example.security.security_config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static java.util.Collections.singletonMap;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return getAuthentication(request.getHeader(SecurityConstants.AUTHORIZATION));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = createJwtToken(user);
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER_ + token);
    }

    private String createJwtToken(UserDetails user) {
        return JWT.create()
                .withAudience(SecurityConstants.TOKEN_AUDIENCE)
                .withIssuer(SecurityConstants.TOKEN_ISSUER)
                .withHeader(singletonMap("typ", SecurityConstants.TOKEN_TYPE))
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .withArrayClaim("roles", getRoles(user))
                .sign(Algorithm.HMAC512(SecurityConstants.AUTHORIZATION_SECRET.getBytes()));
    }

    private String[] getRoles(UserDetails user) {
        String[] strings = user.getAuthorities().stream().map(a -> a.toString()).toArray(String[]::new);
        return strings;
    }


    private Authentication getAuthentication(String header) {
        if (header == null)
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken("", ""));
        header = header.replace(SecurityConstants.BASIC_, "");
        byte[] decode = Base64.getDecoder().decode(header.getBytes(StandardCharsets.UTF_8));
        String decodedCredentials = new String(decode);
        String[] split = decodedCredentials.split(":");
        if (split.length != 2)
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken("", ""));
        String username = split[0];
        String password = split[1];
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }
}
