package com.iqkv.incubator.sample.webfluxcrudsecured.config;

import java.util.List;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationManager implements ReactiveAuthenticationManager {
  private final JwtUtil jwtUtil;

  public AuthenticationManager(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();

    String username;

    try {
      username = jwtUtil.extractUsername(authToken);
    } catch (Exception e) {
      username = null;
      log.error(e.getMessage());
    }

    if (username != null && jwtUtil.validateToken(authToken)) {
      Claims claims = jwtUtil.getClaimsFromToken(authToken);
      List<String> role = claims.get("role", List.class);
      List<SimpleGrantedAuthority> authorities = role.stream()
          .map(SimpleGrantedAuthority::new)
          .toList();
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          username,
          null,
          authorities
      );

      return Mono.just(authenticationToken);
    } else {
      return Mono.empty();
    }
  }
}
