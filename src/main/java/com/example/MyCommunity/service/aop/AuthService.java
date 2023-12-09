package com.example.MyCommunity.service.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  /** 현재 사용자의 아이디를 가져오는 메서드 */
  public String getCurrentUserId(Authentication authentication){
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String userId = userDetails.getUsername();
    return userId;
  }
}
