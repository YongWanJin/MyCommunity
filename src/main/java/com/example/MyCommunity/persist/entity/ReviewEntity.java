package com.example.MyCommunity.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "REVIEW")
@Builder
public class ReviewEntity {

  /** 후기 게시글 ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "REVIEW_ID")
  private Integer reviewId;

  /** 게시글 제목 */
  @Column(name = "TITLE")
  private String title;

  /** 게시글 내용 */
  @Column(name = "CONTENT")
  private String content;

  /** 생성 일자 */
  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  /** 수정 일자 */
  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;

  /** 조회수 */
  @Column(name = "VEIW")
  private Integer view;

  /** 작성자 아이디 */
  // 외래키(FK)를 가지고 있는 게시글 엔티티(ReviewEntity)가 관계의 주인으로 정의된다.
  // ReviewEntity'에서' 유저 엔티티(MemberEntity)를 참조하고 있기 때문이다.
  // 참조하는 주체가 곧 관계의 주인이 된다.
  // 따라서 관계의 주인인 ReviewEntity의 '관점에서' 봤을 때,
  // ReviewEntity와 MemberEntity는 Many to One의 관계를 지닌다.
  // 한명이 여러개의 게시글을 쓸 수 있지만(MemberEntity(One) -> ReviewEntity(Many))
  // 한 게시글에는 한 명의 글쓴이만 존재할 수 있다.(ReveiwEntity(Many) -> MemberEntity(One))
  // (MemberEntity의 관점에서 보면 One to Many 관계를 지닌다고 볼 수 있다. 관점 차이.)
  //
  // 종합해보면, FK가 존재하는 ReviewEntity에서 어노테이션 @JoinColumn을 사용하고,
  // ReviewEntity가 관계의 주인이므로 관계를 나타내는 어노테이션(@ManyToOne)은 ReviewEntity에만 사용했다.(단방향)
  @ManyToOne
  @JoinColumn(name = "USER_ID")
  // MemberEntity의 기본키(PK)를 참조하고 있으므로 이 필드값(userId)의 자료형은 MemberEntity가 된다.
  private MemberEntity member;
  // ERD에 의하면 독서모임 참여현황 테이블의 엔티티(RegisterEntity, 미구현)의 PK를 참조해야하나,
  // 게시글 작성 기능부터 먼저 구현하고 학습하기 위해서
  // 현 시점에서는 유저 엔티티(MemberEntity)의 PK를 일시적으로 참조하였다.
}



















