package com.syscode.addressserviceapplication.jwt;

import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidAuthHeaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenServiceImplTest {

  private final String secretString = "98897acaccacc%%cacaca/cac/=ccaxxxaaaa8768765458787698///=auzh";
  private JwtTokenServiceImpl jwtTokenService;

  @BeforeEach
  public void setup() {
    jwtTokenService = new JwtTokenServiceImpl();
  }

  @Test
  public void validate_should_throwInvalidAuthHeaderException() {
    InvalidAuthHeaderException exception = assertThrows(
        InvalidAuthHeaderException.class,
        () -> jwtTokenService.validate(null),
        "Expecting validate() to throw exception when null is given.");
    exception = assertThrows(InvalidAuthHeaderException.class,
        () -> jwtTokenService.validate(""),
        "Expecting validate() to throw exception when empty String is given.");
    exception = assertThrows(InvalidAuthHeaderException.class,
        () -> jwtTokenService.validate("123"),
        "Expecting validate() to throw exception when String.length() < 7.");
  }

}