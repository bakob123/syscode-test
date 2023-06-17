package com.syscode.profileserviceapplication.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.LinkedHashMap;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

  @Value("${jwt.secret.string}")
  private String secretString;
  @Value("${jwt.token.lifespan}")
  private Long tokenLifeSpan;

  @Override
  public String createJwtToken(LinkedHashMap<String, Object> claims) {
    if (claims == null || claims.isEmpty()) return "";
    SecretKey key = new SecretKeySpec(secretString.getBytes(), "HmacSHA256");
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();
  }

  @Override
  public LinkedHashMap<String, Object> createDefaultClaims() {
    LinkedHashMap<String, Object> claims = new LinkedHashMap<>();
    claims.put("username", "authenticatedUser");
    claims.put("exp", System.currentTimeMillis() + tokenLifeSpan);
    return claims;
  }

}
