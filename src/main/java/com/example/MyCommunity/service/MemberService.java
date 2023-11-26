package com.example.MyCommunity.service;

import com.example.MyCommunity.dto.MemberDto;
import com.example.MyCommunity.dto.consist.Authority;
import com.example.MyCommunity.persist.MemberLeftRepository;
import com.example.MyCommunity.persist.MemberRepository;
import com.example.MyCommunity.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
/**회원 정보 관련 Service*/
public class MemberService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;
//  private final MemberLeftRepository memberLeftRepository;

  /**
   * 회원 정보 불러오기
   */
  @Override
  public UserDetails loadUserByUsername(String UserId) throws UsernameNotFoundException {
    return null;
  }

  /**
   * 회원 가입 로직
   */
  public MemberEntity register(MemberDto.SignUp memberDto) {

    // # 사용 가능한 아이디인지 검사
    // * 중복 아이디
    memberRepository.findByUserId(memberDto.getUserId())
        .ifPresent(id -> {
          throw new RuntimeException(id + "는 이미 존재하는 아이디입니다.");
        });


    // * 탈퇴 회원의 아이디
//    memberLeftRepository.findByUserId(memberDto.getUserId())
//        .ifPresent(id -> {
//          throw new RuntimeException(id + "는 사용할 수 없는 아이디입니다.");
//        });

    // # 패스워드 암호화
    memberDto.setPassword(this.passwordEncoder.encode(memberDto.getPassword()));

    // # 입력값 저장
    MemberEntity result = this.memberRepository.save(memberDto.toEntity());
    return result;
  }
}
