package com.syscode.addressserviceapplication.jwt;

import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidAuthHeaderException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

  @Value("${jwt.secret.string}")
  private String secretString;
  @Value("${jwt.token.lifespan}")
  private String tokenLifeSpan;

  @Override
  public void validate(String authHeader) {
    if (authHeader == null || authHeader.isBlank() || authHeader.length() < 7) throw new InvalidAuthHeaderException();
    String token = authHeader.substring(7);
    SecretKey key = new SecretKeySpec(secretString.getBytes(), "HmacSHA256");
    Jws<Claims> parsedToken = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
  }

}
