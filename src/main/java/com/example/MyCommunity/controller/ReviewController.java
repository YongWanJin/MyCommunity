package com.example.MyCommunity.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 게시판 글쓰기 관련 Controller */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

  /** 새로운 게시글 등록 */
  @PostMapping
  public ResponseEntity<String> writeReview(){
    return ResponseEntity.ok().body("리뷰 게시글 등록 완료(테스트)");
  }
}
