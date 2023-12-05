package com.example.MyCommunity.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MEMBER")
@Builder
/**회원과 관련된 Entity*/
public class MemberEntity implements UserDetails {

  /** 회원 아이디 (PK)*/
  @Id
  @Column(name = "USER_ID")
  private String userId;

  /** 이메일 */
  @Column(name = "EMAIL")
  private String email;

  /** 비밀번호 */
  @Column(name = "PASSWORD")
  private String password;

  /** 필명(닉네임) */
  @Column(name = "NAME")
  private String name;

  /** 가입 날짜 */
  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  /** 권한 */
  @Column(name = "ROLE")
  private String role;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
    auth.add(new SimpleGrantedAuthority(role));
    return auth;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.userId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
