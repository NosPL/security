package com.example.security.security_config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.security.users.UserAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isNotBlank;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (isValid(header)) {
            SecurityContextHolder.getContext().setAuthentication(from(header));
            filterChain.doFilter(request, response);
        } else
            response.setStatus(UNAUTHORIZED.value());
    }

    private UsernamePasswordAuthenticationToken from(String header) {
        String token = header.replace(SecurityConstants.BEARER_, "");
        try {
            DecodedJWT jwt = JwtUtils.getJwt(token, SecurityConstants.AUTHORIZATION_SECRET);
            String username = jwt.getSubject();
            List<UserAuthority> authorities = getAuthorities(jwt);
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (JwtUtils.JwtUtilsException | IllegalArgumentException e) {
            return new UsernamePasswordAuthenticationToken(null, null);
        }
    }

    private List<UserAuthority> getAuthorities(DecodedJWT jwt) {
        String[] roles = jwt.getClaim("roles").asArray(String.class);
        List<UserAuthority> authorities;
        authorities = Arrays.stream(roles).map(UserAuthority::valueOf).collect(Collectors.toList());
        return authorities;
    }

    private boolean isValid(String header) {
        return isNotBlank(header) && header.startsWith(SecurityConstants.BEARER_);
    }
}