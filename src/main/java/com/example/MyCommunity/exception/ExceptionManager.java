package com.example.MyCommunity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 예외 처리를 담당하는 클래스 */
@RestControllerAdvice
public class ExceptionManager {
  @ExceptionHandler(RuntimeException.class)
  /** 예외 발생 시 409 코드와 함께, 각 예외 상황에서 설정한 메시지를 출력한다. */
  public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(e.getMessage());
  }
}
