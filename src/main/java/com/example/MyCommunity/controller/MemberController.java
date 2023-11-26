package com.example.MyCommunity.controller;

import com.example.MyCommunity.dto.MemberDto;
import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 회원 정보 관련 Controller */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

  private final MemberService memberService;

  /**회원 가입 요청 - 회원 가입된 정보들을 리턴한다.*/
  @PostMapping("/signup")
  public ResponseEntity<MemberEntity> signup(@RequestBody MemberDto.SignUp request){
    MemberEntity result = this.memberService.register(request);
    return ResponseEntity.ok().body(result);
  }

  /**로그인 요청 - 토큰을 리턴한다.*/
  @PostMapping("/signin")
  public ResponseEntity<String> singin() {
    return ResponseEntity.ok().body("token");
  }

  @PostMapping("/test")
  public ResponseEntity<String> test(){
    return ResponseEntity.ok().body("로그인한 회원이 맞습니다.");
  }

  /**로그아웃 요청*/

  /**비밀번호 변경 요청*/

  /**필명 변경 요청*/

  /**회원 탈퇴 요청*/

}
