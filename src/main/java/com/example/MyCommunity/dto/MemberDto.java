package com.example.MyCommunity.dto;

import com.example.MyCommunity.persist.entity.MemberEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 회원과 관련된 DTO를 모아둔 클래스 */
public class MemberDto {



  /** 회원 탈퇴에 사용될 DTO 객체*/
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DropOut{
    /** 회원 아이디 */
    private String userId;
    /** 비밀번호 */
    private String password;
  }
}
