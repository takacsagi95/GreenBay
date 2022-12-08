package com.example.GreenBay.security;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.GreenBay.exceptions.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

  // Study about JWT web tokens: https://jwt.io/introduction
  @Override
  protected void doFilterInternal(HttpServletRequest request, // Request to be validated
      @NotNull
      HttpServletResponse response, // the response -->it will be the content or an error message
      FilterChain filterChain // filterChain from SecurityConfig
      // it is  responsible for all the security (protecting the application URLs,
      // validating submitted username and passwords, redirecting to the login form
  ) throws ServletException, IOException {
    // Header contains the information for authorization
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    // trying to check the authorization header
    try {
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        // We need the information from the token behind the Bearer string?
        String token = authorizationHeader.substring("Bearer ".length());
        // Load data from .env file
        Dotenv dotenv = Dotenv.load();
        // Using an algorithm that verifies the the third part of the token (Signiture)
        Algorithm algorithm = Algorithm.HMAC256(
            Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")).getBytes(StandardCharsets.UTF_8));
        // A verifier that using the previously created algorithm
        JWTVerifier verifier = JWT.require(algorithm).build();
        // Decode the information from token's payload
        DecodedJWT decodedJWT = verifier.verify(token);
        // Extract the user's username from token:
        String username = decodedJWT.getSubject();
        // Extract the user's role from token:
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        // Roles need to be converted to "Security format" --> stream
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(
            role -> authorities.add(new SimpleGrantedAuthority(role))
        );
        // Authentication in SecurityContentHolder
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // filter-chain accepts the request
        filterChain.doFilter(request, response);
      } else {
        // if there is no authorization header --> Error message
        throw new UnauthorizedException("Access denied.");
      }
    } catch (Exception e) { // All other exception will be caught here
      // Exception message ---> header
      response.setHeader("error", e.getMessage());
      // Setting the status code:
      response.setStatus(FORBIDDEN.value());
      // Putting exception message into body as well
      Map<String, String> error = new HashMap<>();
      response.setContentType(APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
  }
}
