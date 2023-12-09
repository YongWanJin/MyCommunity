package com.example.MyCommunity.controller;

import com.example.MyCommunity.dto.reviewDto.Review;
import com.example.MyCommunity.persist.entity.ReviewEntity;
import com.example.MyCommunity.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 게시판 글쓰기 관련 Controller */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  /** 새로운 후기 게시글 등록 */
  @PostMapping("/posts")
  public ResponseEntity<Review.Response> writeReview(@RequestBody @Valid Review.Request request, Authentication authentication){
    Review.Response result = reviewService.writeReview(request, authentication);
    return ResponseEntity.ok().body(result);
  }
}
