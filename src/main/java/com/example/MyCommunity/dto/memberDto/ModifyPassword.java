package com.example.MyCommunity.dto.memberDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 비밀번호 변경시에 사용될 DTO 객체*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPassword {
  /** 예전 비밀번호 */
  private String oldPassword;
  /** 새 비밀번호 */
  private String newPassword;
  /** 새 비밀번호 재입력 (확인용)*/
  private String newPasswordConfirmation;
}

