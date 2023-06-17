package com.syscode.profileserviceapplication.jwt;

import java.util.LinkedHashMap;

public interface JwtTokenService {

  String createJwtToken(LinkedHashMap<String, Object> claims);

  LinkedHashMap<String, Object> createDefaultClaims();

}
