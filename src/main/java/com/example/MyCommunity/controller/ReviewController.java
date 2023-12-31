package com.example.MyCommunity.controller;

import com.example.MyCommunity.dto.reviewDto.DeleteReview;
import com.example.MyCommunity.dto.reviewDto.ReadReview;
import com.example.MyCommunity.dto.reviewDto.UpdateReview;
import com.example.MyCommunity.dto.reviewDto.WriteReview;
import com.example.MyCommunity.persist.entity.ReviewEntity;
import com.example.MyCommunity.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 게시판 관련 Controller */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  /** 새로운 후기 게시글 등록 */
  @PostMapping("/posts")
  public ResponseEntity<WriteReview.Response> writeReview(@RequestBody @Valid WriteReview.Request request, Authentication authentication){
    // authentication을 통해 현재 사용자 정보를 받아온다.
    WriteReview.Response result = reviewService.writeReview(request, authentication);
    return ResponseEntity.ok().body(result);
  }

  /** 게시글 수정 */
  @PutMapping("/posts")
  public ResponseEntity<UpdateReview.Response> updateReview(@RequestBody @Valid UpdateReview.Request request, Authentication authentication){
    UpdateReview.Response result = reviewService.updateReview(request, authentication);
    return ResponseEntity.ok().body(result);
  }

  /** 게시글 삭제 */
  @DeleteMapping("/posts")
  public ResponseEntity<String> deleteReview(@RequestBody @Valid DeleteReview.Request request, Authentication authentication){
    reviewService.deleteReview(request, authentication);
    return ResponseEntity.ok().body("게시글 삭제가 완료되었습니다.");
  }

  /** 게시글 열람 (한 포스팅) */
  @GetMapping("/reading") // [질문] '열람', '조회' 등 명사인데 행위를 나타내는 것들도 url에 사용할 수 있나요?
  public ResponseEntity<ReadReview.Response> readReview(@RequestBody @Valid ReadReview.Request request){
    ReadReview.Response result = reviewService.readReview(request);
    return ResponseEntity.ok().body(result);
  }

}
