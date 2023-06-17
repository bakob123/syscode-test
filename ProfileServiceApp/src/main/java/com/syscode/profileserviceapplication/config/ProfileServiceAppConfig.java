package com.syscode.profileserviceapplication.config;

import com.syscode.profileserviceapplication.jwt.JwtTokenService;
import com.syscode.profileserviceapplication.jwt.JwtTokenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProfileServiceAppConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public JwtTokenService jwtTokenService() {
    return new JwtTokenServiceImpl();
  }

}
