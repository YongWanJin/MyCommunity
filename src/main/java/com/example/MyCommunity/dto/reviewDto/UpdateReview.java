package com.example.MyCommunity.dto.reviewDto;

import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.persist.entity.ReviewEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 게시글 수정 시에 사용될 dto */
public class UpdateReview {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  /** 게시글 수정에 필요한 데이터를 받는 dto */
  public static class Request{
    private Integer reviewId;
    private String title;
    private String content;
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  /** 작성 완료 후 결과 출력용 dto */
  public static class Response{
    /** 게시글 고유 번호 */
    private Integer reviewId;
    /** 게시글 제목 */
    private String title;
    /** 게시글 내용 */
    private String content;
    /** 게시글 최초 작성일자 */
    private LocalDateTime createdAt;
    /** 게시글 수정일자 */
    private LocalDateTime updatedAt;
    /** 조회수 */
    private Integer view;
    /** 글쓴이 아이디 */
    private String userId;
    /** 글쓴이 필명 */
    private String name;
  }
}
