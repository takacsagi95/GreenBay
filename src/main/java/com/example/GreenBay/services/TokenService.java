package com.example.GreenBay.services;

import com.example.GreenBay.models.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface TokenService {
  User getUserFromAuthorizationHeader(String bearerToken);

  String getToken(UserDetails userDetails);

  // Get Token method
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
