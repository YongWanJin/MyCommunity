package com.example.MyCommunity.controller;

import com.example.MyCommunity.dto.memberDto.DropOut;
import com.example.MyCommunity.dto.memberDto.ModifyName;
import com.example.MyCommunity.dto.memberDto.ModifyPassword;
import com.example.MyCommunity.dto.memberDto.SignIn;
import com.example.MyCommunity.dto.memberDto.SignUp;
import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  /**회원 가입 요청*/
  @PostMapping("/signup")
  public ResponseEntity<SignUp.Response> signup(@RequestBody @Valid SignUp.Request request){
    SignUp.Response result = this.memberService.register(request);
    return ResponseEntity.ok().body(result);
  }

  /**로그인 요청 - 토큰을 리턴한다.*/
  @PostMapping("/signin")
  public ResponseEntity<String> singin(@RequestBody @Valid SignIn request) {
    String token = this.memberService.login(request);
    return ResponseEntity.ok().body(token);
  }

  /**로그아웃 요청*/
  @GetMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request){
    this.memberService.logout();
    return ResponseEntity.ok().body("로그아웃 완료.(미완성)");
  }


  /**비밀번호 변경 요청*/
  @PutMapping("/update/password")
  public ResponseEntity<String> updatePassword(@RequestBody @Valid ModifyPassword request, Authentication authentication){
    this.memberService.updatePassword(request, authentication);
    // !! 로그아웃 기능도 이곳에 추가할 것.
    return ResponseEntity.ok().body("비밀번호 변경 완료. 다시 로그인해주십시오.");
  }

  /**필명 변경 요청*/
  @PutMapping("/update/name")
  public ResponseEntity<String> updateName(@RequestBody @Valid ModifyName request, Authentication authentication){
    this.memberService.updateName(request, authentication);
    return ResponseEntity.ok().body("이름 변경 완료");
  }

  /**회원 탈퇴 요청*/
  @PutMapping("/dropout")
  public ResponseEntity<String> dropout(@RequestBody @Valid DropOut request, Authentication authentication){
    this.memberService.dropOut(request, authentication);
    // 로그아웃 기능도 이곳에 추가할 것
    return ResponseEntity.ok().body("회원 탈퇴가 완료되었습니다.");
  }

}
