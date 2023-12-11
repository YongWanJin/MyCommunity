package com.example.MyCommunity.dto.reviewDto;

import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.persist.entity.ReviewEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


/** Reveiw Dto */
public class Review {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  /** 게시글 입력용 dto */
  public static class Request{
    private String title;
    private String content;

    /** 입력한 게시글을 entity로 변환해주는 메서드 */
    public ReviewEntity toEntity(MemberEntity memberEntity){
      return ReviewEntity.builder()
          .title(this.title)
          .content(this.content)
          .createdAt(LocalDateTime.now())
          .updatedAt(LocalDateTime.now())
          .view(0)
          .member(memberEntity)
          .build();
    }

  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  /** 게시글 결과 출력용 dto */
  public static class Response{
    private Integer reviewId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer view;
    private String userId;
  }
}
