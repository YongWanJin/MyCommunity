package com.example.MyCommunity.aop;

import com.example.MyCommunity.persist.MemberRepository;
import com.example.MyCommunity.persist.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect implements UserDetailsService {

  private final MemberRepository memberRepository;

  /** 회원 상세 정보 불러오기 :
  아이디, 비밀번호 외의 정보를 불러오기 위해서는 MemberEntity 타입으로 형변환 해야한다. */
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return this.memberRepository.findByUserId(userId)
        .orElseThrow(()-> new UsernameNotFoundException("유저를 찾을 수 없습니다. " + userId));
  }
}
