package com.example.MyCommunity.service;

import com.example.MyCommunity.dto.memberDto.ModifyName;
import com.example.MyCommunity.dto.memberDto.ModifyPassword;
import com.example.MyCommunity.dto.memberDto.SignIn;
import com.example.MyCommunity.dto.memberDto.SignUp;
import com.example.MyCommunity.exception.AppException;
import com.example.MyCommunity.exception.ErrorCode;
import com.example.MyCommunity.persist.MemberRepository;
import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
/**회원 정보 관련 Service*/
public class MemberService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;
//  private final MemberLeftRepository memberLeftRepository;

  /**시크릿 키*/
  @Value("${jwt.token.secretKey}")
  private String secretKey;
  /**토큰 만료 시각*/
  private Long expiredAtMs = 1000 * 60 * 60L;


  /** 회원 정보 불러오기 */
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return this.memberRepository.findByUserId(userId)
        .orElseThrow(()-> new UsernameNotFoundException("유저를 찾을 수 없습니다. " + userId));
  }


  /** 회원 가입 로직 */
  public SignUp.Response register(SignUp.Request request) {

    // # case 1. 이미 존재하는 아이디인지 확인
    // !! 커스텀 예외 처리로 바꿀것.
    memberRepository.findByUserId(request.getUserId())
        .ifPresent(id -> {
          throw new RuntimeException(id + "는 이미 존재하는 아이디입니다.");
        });


    // * case 2. 사용 불가능한 아이디(탈퇴 회원의 아이디)인지 확인
    // !! 나중에 구현할 것.
//    memberLeftRepository.findByUserId(memberDto.getUserId())
//        .ifPresent(id -> {
//          throw new RuntimeException(id + "는 사용할 수 없는 아이디입니다.");
//        });

    // # 패스워드 암호화
    request.setPassword(this.passwordEncoder.encode(request.getPassword()));

    // # 입력값 저장
    MemberEntity result = this.memberRepository.save(request.toEntity());

    // # 결과 반환
    return SignUp.Response
        .builder()
        .userId(result.getUserId())
        .email(result.getEmail())
        .name(result.getName())
        .build();
  }

  /** 로그인 로직 */
  public String login(SignIn request){

    String inputUserId = request.getUserId();
    String inputPassword = request.getPassword();

    // # case 1. userId 존재하지 않음
    // 사용자가 입력한 아이디 값(inputUserId)이 DB에 존재하는지 여부를 확인한다.
    // 존재하지 않으면 예외처리를 한다.
    MemberEntity selectedMember = memberRepository.findByUserId(inputUserId)
        .orElseThrow(() ->new AppException(ErrorCode.USERID_NOTFOUND, inputUserId + "가 존재하지 않습니다."));

    // # case 2. password 틀림
    // userId가 DB에 존재해서 selectedMember가 정상적으로 리턴되었을 경우,
    // 사용자가 입력한 패스워드(inputPassword)가 DB에 저장된 패스워드와 일치하는지 비교한다.
    // 일치하지 않으면 예외처리를 한다.
    // (DB의 패스워드는 암호화되어 저장되어있기 때문에 passwordEncoder 객체의 매서드 matches를 이용해야한다.)
    if(!passwordEncoder.matches(inputPassword, selectedMember.getPassword())) {
      throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력하였습니다.");
    }

    // # 토큰 발행
    String token = JwtUtil.createJwtToken(selectedMember.getUserId(), secretKey, expiredAtMs);
    return token;
  }

  /** 로그아웃 로직 */
  public void logout() {
    // 작성중
  }

  /** 비밀번호 변경 로직 */
  public MemberEntity updatePassword(ModifyPassword memberDto) {

    // # case 1. 옛 비밀번호를 잘못 입력함

    // # case 2. 새로 입력한 비밀번호가 옛 비밀번호와 동일함

    // # 변경사항 저장
    return null;

  }

  /** 이름 변경 로직 */
  public MemberEntity updateName(ModifyName requset) {
    // # 변경사항 저장
    return null;
  }

  /** 회원 탈퇴 로직 */
}
