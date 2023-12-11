package com.example.MyCommunity.dto.reviewDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 게시글 삭제 시에 사용될 dto */
public class DeleteReview {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  /** 게시글 삭제에 필요한 데이터를 받는 dto */
  public static class Request{
    private Integer reviewId;
  }
}
