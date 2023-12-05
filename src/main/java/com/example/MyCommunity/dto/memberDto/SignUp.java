package com.example.MyCommunity.dto.memberDto;

import com.example.MyCommunity.persist.entity.MemberEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 회원 가입시에 사용될 dto 객체*/
public class SignUp {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request{
    /** 회원 아이디 */
    private String userId;
    /** 이메일 */
    private String email;
    /** 비밀번호 */
    private String password;
    /** 필명(닉네임) */
    private String name;
    /** 가입 날짜 */
    private LocalDateTime createdAt;
    /** 권한 */
    private String role;

    /** SignUp DTO 객체를 엔티티로 변환 */
    public MemberEntity toEntity() {
      return MemberEntity.builder()
          .userId(this.userId)
          .email(this.email)
          .password(this.password)
          .name(this.name)
          .createdAt(LocalDateTime.now())
          .role(this.role)
          .build();
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response{
    /** 회원 아이디 */
    private String userId;
    /** 이메일 */
    private String email;
    /** 필명(닉네임) */
    private String name;
  }

}
