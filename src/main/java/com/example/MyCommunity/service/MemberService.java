package com.example.MyCommunity.service;

import com.example.MyCommunity.dto.memberDto.DropOut;
import com.example.MyCommunity.dto.memberDto.ModifyName;
import com.example.MyCommunity.dto.memberDto.ModifyPassword;
import com.example.MyCommunity.dto.memberDto.SignIn;
import com.example.MyCommunity.dto.memberDto.SignUp;
import com.example.MyCommunity.exception.AppException;
import com.example.MyCommunity.exception.ErrorCode;
import com.example.MyCommunity.persist.MemberRepository;
import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.security.JwtUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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

    // # case 1. 사용 불가능한 아이디인지 확인
    memberRepository.findByUserId(request.getUserId())
        .ifPresent(e -> {
          throw new AppException(ErrorCode.USERID_DUPLICATED, "이미 존재하는 아이디입니다.");
        });

    // # case 2. 사용 불가능한 이메일인지 확인
    memberRepository.findByEmail(request.getEmail())
        .ifPresent(e -> {
          throw new AppException(ErrorCode.EMAIL_DUPLICATED, "이미 존재하는 이메일입니다.");
        });

    // # case 3. 사용 불가능한 필명인지 확인
    memberRepository.findByName(request.getName())
        .ifPresent(e -> {
          throw new AppException(ErrorCode.EMAIL_DUPLICATED, "이미 존재하는 이름입니다.");
        });

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
        .orElseThrow(() ->new AppException(ErrorCode.USERID_NOTFOUND, "존재하지 않는 아이디입니다."));

    // # case 2. 이미 탈퇴된 회원임
    // 탈퇴를 하여 더이상 사용할 수 없는 아이디로 로그인할 경우 예외 처리를 한다.
    if(!selectedMember.isEnabled()){
      throw new AppException(ErrorCode.USERID_INVALID, "존재하지 않는 아이디입니다.");
    }

    // # case 3. password 틀림
    // userId가 DB에 존재해서 selectedMember가 정상적으로 리턴되었을 경우,
    // 사용자가 입력한 패스워드(inputPassword)가 DB에 저장된 패스워드와 일치하는지 비교한다.
    // 일치하지 않으면 예외처리를 한다.
    // (DB의 패스워드는 암호화되어 저장되어있기 때문에 passwordEncoder 객체의 매서드 matches를 이용해야한다.)
    if(!passwordEncoder.matches(inputPassword, selectedMember.getPassword())) {
      throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력하였습니다.");
    }

    // # 토큰 생성
    String token = JwtUtil.createJwtToken(selectedMember.getUserId(), secretKey, expiredAtMs);

    // # 레디스에 등록(로그인 중인지 확인하는 용도)

    // # 토큰 발행
    return token;
  }

  /** 로그아웃 로직 */
  public void logout() {
    // 작성중
  }

  /** 비밀번호 변경 로직 */
  public void updatePassword(ModifyPassword request, Authentication authentication) {
    // 참고한 코드 : https://magicmk.tistory.com/34

    String oldPassword = request.getOldPassword();
    String newPassword = request.getNewPassword();
    String doubleCheck = request.getNewPasswordConfirmation();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String curPassword = userDetails.getPassword(); // 암호화 되어있음

    // # case 1. 옛 비밀번호(oldPassword)를 잘못 입력함 (현재 비밀번호인 curPassword와 비교)
    if(!this.passwordEncoder.matches(oldPassword, curPassword)) {
      throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력하였습니다.");
    }

    // # case 2. 새로 입력한 비밀번호(newPassword)가 옛 비밀번호(oldPassword)와 동일함
    if(newPassword.equals(oldPassword)){
      throw new AppException(ErrorCode.PASSWORD_NOT_CHANGED, "새로운 패스워드를 입력해주십시오.");
    }

    // # case 3. 최초로 입력한 새 비밀번호(newPassword)와 재입력한 새 비밀번호(doubleCheck)가 일치하지 않음
    if(!doubleCheck.equals(newPassword)){
      throw new AppException(ErrorCode.PASSWORD_NOT_CONFIRM, "새 비밀번호와 확인용 비밀번호가 일치하지 않습니다.");
    }

    // # 새 비밀번호 암호화
    request.setNewPassword(this.passwordEncoder.encode(newPassword));

    // # 변경사항 적용 및 저장
    // 기존에 있던 엔티티를 불러온 후 덮어쓰는 방식으로 정보를 갱신한다.
    MemberEntity curMember = memberRepository.findByUserId(userDetails.getUsername())
        .orElseThrow(() ->new AppException(ErrorCode.USERID_NOTFOUND, "유효한 사용자가 아닙니다. 다시 로그인해주십시오."));
    curMember.setPassword(request.getNewPassword());
    curMember.setUpdatedAt(LocalDateTime.now());
    this.memberRepository.save(curMember);
  }

  /** 이름 변경 로직 */
  public void updateName(ModifyName requset, Authentication authentication) {

    String inputPassword = requset.getPassword();
    String newName = requset.getNewName();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String curPassword = userDetails.getPassword(); // 암호화 되어있음

    // # case1. 보안을 위해 입력한 패스워드(inputPassword)가 실제 패스워드(curPassword)와 일치하지 않음
    if(!this.passwordEncoder.matches(inputPassword, curPassword)) {
      throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력하였습니다.");
    }

    // # case 2. 이미 존재하는 필명인지 확인
    memberRepository.findByName(newName)
        .ifPresent(e -> {
          throw new AppException(ErrorCode.EMAIL_DUPLICATED, "이미 존재하는 이름입니다.");
        });

    // # 변경사항 적용 및 저장
    MemberEntity curMember = memberRepository.findByUserId(userDetails.getUsername())
        .orElseThrow(() ->new AppException(ErrorCode.USERID_NOTFOUND, "유효한 사용자가 아닙니다. 다시 로그인해주십시오."));
    curMember.setName(newName);
    curMember.setUpdatedAt(LocalDateTime.now());
    this.memberRepository.save(curMember);

  }

  /** 회원 탈퇴 로직 */
  public void dropOut(DropOut request, Authentication authentication) {
    // 탈퇴한 회원과 신규 회원의 아이디가 동일할 경우를 피하기 위해서
    // 탈퇴한 회원을 DB에서 완전히 삭제하지 않고, 계정 활성화 여부만 체크한다.
    // (탈퇴한 회원과 신규 회원의 아이디가 같을 경우,
    // 예전 게시글들 중 탈퇴한 회원이 작성한 글인지 신규 회원이 작성한 글인지 구분할 수 없는 경우가
    // 발생할 수 있기 때문이다.)

    String inputPassword = request.getPassword();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String curPassword = userDetails.getPassword(); // 암호화 되어있음

    // # case1. 보안을 위해 입력한 패스워드(inputPassword)가 실제 패스워드(curPassword)와 일치하지 않음
    if(!this.passwordEncoder.matches(inputPassword, curPassword)) {
      throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력하였습니다.");
    }

    // # 탈퇴 적용
    MemberEntity curMember = memberRepository.findByUserId(userDetails.getUsername())
        .orElseThrow(() ->new AppException(ErrorCode.USERID_NOTFOUND, "유효한 사용자가 아닙니다. 다시 로그인해주십시오."));
    curMember.setEnabled(false);
    curMember.setLeftAt(LocalDateTime.now());
    this.memberRepository.save(curMember);
  }
}
