package com.syscode.addressserviceapplication.jwt;

public interface JwtTokenService {
  void validate(String authHeader);

}
