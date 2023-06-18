package com.syscode.addressserviceapplication.address.controllers;

import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidAuthHeaderException;
import com.syscode.addressserviceapplication.jwt.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AddressControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private MediaType contentType = MediaType.APPLICATION_JSON;
  @Autowired
  private JwtTokenService jwtTokenService;
  private String token =
      "eyJhbGciOiJIUzI1NiJ9" +
      ".eyJ1c2VybmFtZSI6InN0ZyIsImV4cCI6MjE0NTk2MjE4NTAwMH0" +
      ".QoSmo5V9oGw4lIj7IPPV-5d4Y4b8Df6knJQsqc5sRMs";

  @Test
  public void getAddress_should_returnStatus400_when_authHeaderIsInvalidFormat() throws Exception {
    mockMvc.perform(get("/api/address/1234567")
            .header("Authorization", "invalid"))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is(InvalidAuthHeaderException.MESSAGE)));
  }

  @Test
  public void getAddress_should_returnCorrectAddress() throws Exception {
    UUID uuid = UUID.randomUUID();
    mockMvc.perform(get("/api/address/" + uuid)
            .header("Authorization", "Bearer " + token))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(uuid.toString())))
        .andExpect(jsonPath("$.address", is("Győr")));
  }

}