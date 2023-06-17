package com.syscode.addressserviceapplication.jwt;

import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidAuthHeaderException;
import io.jsonwebtoken.JwtException;

public interface JwtTokenService {
  void validate(String authHeader) throws InvalidAuthHeaderException, JwtException;

}
