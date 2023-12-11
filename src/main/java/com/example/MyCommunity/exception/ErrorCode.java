package com.example.MyCommunity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  USERID_DUPLICATED(HttpStatus.CONFLICT, ""),
  USERID_NOTFOUND(HttpStatus.NOT_FOUND, ""),
  USERID_INVALID(HttpStatus.CONFLICT, ""),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
  PASSWORD_NOT_CHANGED(HttpStatus.CONFLICT, ""),
  PASSWORD_NOT_CONFIRM(HttpStatus.CONFLICT, ""),
  EMAIL_DUPLICATED(HttpStatus.CONFLICT, ""),
  NAME_DUPLICATED(HttpStatus.CONFLICT, ""),
  REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "")
  ;

  private HttpStatus httpStatus;
  private String message;
}
