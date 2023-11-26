package com.example.MyCommunity.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LEAVE_MEMBER")
@Builder
public class MemberLeftEntity {

  @Id
  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "LEAVE_AT")
  private LocalDateTime leaveAt;
}
