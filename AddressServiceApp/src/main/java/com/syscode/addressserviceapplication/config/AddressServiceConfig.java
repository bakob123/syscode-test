package com.syscode.addressserviceapplication.config;

import com.syscode.addressserviceapplication.jwt.JwtTokenService;
import com.syscode.addressserviceapplication.jwt.JwtTokenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressServiceConfig {

  @Bean
  public JwtTokenService jwtTokenService() {
    return new JwtTokenServiceImpl();
  }

}
