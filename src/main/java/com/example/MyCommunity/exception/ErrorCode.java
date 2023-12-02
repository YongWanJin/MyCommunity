package com.example.MyCommunity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
  USERID_NOTFOUND(HttpStatus.NOT_FOUND, ""),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "");

  private HttpStatus httpStatus;
  private String message;
}
