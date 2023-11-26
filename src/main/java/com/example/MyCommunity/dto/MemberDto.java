package com.example.MyCommunity.dto;

import com.example.MyCommunity.persist.entity.MemberEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 회원과 관련된 DTO를 모아둔 클래스 */
public class MemberDto {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  /** 회원가입시에 사용될 DTO 객체 */
  public static class SignUp{
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

  /** 로그인시에 사용될 DTO 객체*/
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SignIn{
    /** 회원 아이디 */
    private String userId;
    /** 비밀번호 */
    private String password;
  }
}
