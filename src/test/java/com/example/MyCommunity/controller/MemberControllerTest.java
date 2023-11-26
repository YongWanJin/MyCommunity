package com.example.MyCommunity.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.MyCommunity.dto.MemberDto;
import com.example.MyCommunity.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


/** MemberController 테스트용 */
@WebMvcTest // Mock Test를 위한 어노테이션
class MemberControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean // memberService라는 테스트용 가짜 Bean을 생성
  MemberService memberService;

  @Autowired
  ObjectMapper objectMapper; // 자바 오브젝트를 json으로 만들어주는 Mapper 객체

  @Test
  @DisplayName("회원가입 성공")
  @WithMockUser
  void signin() throws Exception{
    // 입력받을 값들
    String userId = "abcd";
    String email = "asdf@naver.com";
    String password = "1234";
    String name = "MyName";
    LocalDateTime localDateTime = LocalDateTime.now();
    String role = "USER";

    // 해당 url의 mock 테스트 실행
    mockMvc.perform(post("/auth/signup")
            // 401 및 403에러 방지용
            .with(csrf())
        // 결과를 json으로 반환
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(
            // 회원가입용 Dto의 객체 생성 (가독성을 위해 Builder 패턴 적용)
            MemberDto.SignUp.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .name(name)
                .createdAt(localDateTime)
                .role(role)
                .build()
        )))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("회원가입 실패")
  @WithMockUser
  void signin_fail() throws Exception{
    String userId = "eeee";
    String email = "asdf@naver.com";
    String password = "1234";
    String name = "MyName";
    LocalDateTime localDateTime = LocalDateTime.now();
    String role = "USER";

    when(memberService.signup())

    mockMvc.perform(post("/auth/signup")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(
                MemberDto.SignUp.builder()
                    .userId(userId)
                    .email(email)
                    .password(password)
                    .name(name)
                    .createdAt(localDateTime)
                    .role(role)
                    .build()
            )))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
