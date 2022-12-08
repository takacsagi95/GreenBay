package com.example.GreenBay.services;

import static com.example.GreenBay.security.SecurityConfig.TOKEN_EXPIRATION_TIME;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.GreenBay.models.entities.User;
import com.example.GreenBay.repositories.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class TokenServiceImpl implements TokenService {
  private final UserRepository userRepository;

  public TokenServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUserFromAuthorizationHeader(String bearerToken) {
    Dotenv dotenv = Dotenv.load(); // Loading the .env file with its configurations
    String token = bearerToken.substring("Bearer ".length()); // loading the bearer token
    // Take the secret JWT key from the .env file
    Algorithm algorithm = Algorithm.HMAC256(
        Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")).getBytes(
            StandardCharsets.UTF_8));
    //  Building a verifier for token verification:
    JWTVerifier verifier = JWT.require(algorithm).build();
    // Verifying the token:
    DecodedJWT decodedJWT = verifier.verify(token);
    // Getting the username which is stored in the subject
    String username = decodedJWT.getSubject();
    // Returning the user based on the username from bearer token:
    return userRepository.findUserByUsername(username);
  }

  @Override
  public String getToken(UserDetails userDetails) {
    Dotenv dotenv = Dotenv.load();
    Algorithm algorithm = Algorithm.HMAC256(
        Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")).getBytes(StandardCharsets.UTF_8));
    return JWT.create()
        .withSubject(userDetails.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
        // Saving url path into JWT // Add specific issuer to payload
        .withIssuer(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("loggingUser/login")
                .toUriString())
        // Saving roles into JWT
        .withClaim("roles",
            userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
                Collectors.toList()))
        // Signing JWT with algorithm from JWT_SECRET_KEY
        .sign(algorithm);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found in the database");
    }
    // SimpleGrantedAuthority: Stores a String representation of an authority granted to the Authentication object.
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), authorities);
  }
}
