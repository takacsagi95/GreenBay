package com.example.GreenBay.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfig {

  public static final int TOKEN_EXPIRATION_TIME = 3600000;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web -> web.ignoring().antMatchers("/user/**")); // antMatchers --> for my specified urss
  }

  // https://www.baeldung.com/spring-security-csp
  // More about SecurityFilterChain: -->
  // https://docs.spring.io/spring-security/site/docs/3.1.4.RELEASE/reference/security-filter-chain.html
  // This bean defines the Spring Security configuration through the security filter chain:
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable();
    httpSecurity.addFilterBefore(
        new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    httpSecurity.csrf().disable();
    httpSecurity
        // returns the header for further customization
        .headers()
        // For customizing the XXssProtectionHeaderWriter which adds the X-XSS_protection header
        .xssProtection()
        // allows the complementing of the custom configuration
        .and()
        // enables the Content-Security-Policy headir in the response using the supplied security policy directives
        // script-src: specifies the browser to load the scripts only from the same origin
        // form-action 'self' : restrict the form actions to the same origin
        .contentSecurityPolicy("script-src 'self'; form-action 'self'")
        .and()
        // includes the Permissions-Policiy header in the response using the supplied policy directives
        .permissionsPolicy(
            permissionsPolicyConfig -> permissionsPolicyConfig.policy("geolocation = (self)"));
    return httpSecurity.build();
  }
}
