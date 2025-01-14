/*
 * Copyright 2024 IQKV Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iqkv.incubator.sample.reactivecrudsecured.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.iqkv.incubator.sample.reactivecrudsecured.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final JwtTokenProperties jwtTokenProperties;

  public String extractUsername(String authToken) {
    return getClaimsFromToken(authToken)
        .getSubject();
  }

  public Claims getClaimsFromToken(String authToken) {
    String key = Base64.getEncoder().encodeToString(jwtTokenProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    return Jwts.parser()
        .setSigningKey(key)
        .build()
        .parseSignedClaims(authToken)
        .getPayload();
  }

  public boolean validateToken(String authToken) {
    return getClaimsFromToken(authToken)
        .getExpiration()
        .after(new Date());
  }

  public String generateToken(User user) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("role", List.of(user.getRole()));

    long expirationSeconds = jwtTokenProperties.getExpiration();
    Date creationDate = new Date();
    Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getName())
        .setIssuedAt(creationDate)
        .setExpiration(expirationDate)
        .signWith(Keys.hmacShaKeyFor(jwtTokenProperties.getSecret().getBytes(StandardCharsets.UTF_8)))
        .compact();
  }
}
