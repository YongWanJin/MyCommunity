package com.example.MyCommunity.dto.memberDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 이름 변경시에 사용될 DTO 객체*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyName {
  /** 비밀번호 */
  private String password;
  /** 새로 바꿀 이름 */
  private String newName;
}
