package com.example.MyCommunity.dto.memberDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/** 회원 탈퇴에 사용될 DTO 객체*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DropOut {
  /** 회원 아이디 */
  private String userId;
  /** 비밀번호 */
  private String password;
}
