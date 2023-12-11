package com.example.MyCommunity.service;

import com.example.MyCommunity.dto.reviewDto.ReadReview;
import com.example.MyCommunity.dto.reviewDto.ReadReview.Response;
import com.example.MyCommunity.service.aop.AuthService;
import com.example.MyCommunity.dto.reviewDto.DeleteReview;
import com.example.MyCommunity.dto.reviewDto.UpdateReview;
import com.example.MyCommunity.dto.reviewDto.WriteReview;
import com.example.MyCommunity.exception.AppException;
import com.example.MyCommunity.exception.ErrorCode;
import com.example.MyCommunity.persist.MemberRepository;
import com.example.MyCommunity.persist.ReviewRepository;
import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.persist.entity.ReviewEntity;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;
  private final AuthService authService; // [질문] AOP 관점 코딩... 이렇게 하는 것이 맞나요??

  /** 게시글 작성 */
  public WriteReview.Response writeReview(WriteReview.Request request, Authentication authentication) {

    // # 현재 사용자의 엔티티(memberEntity)를 가져온다.
    // (ReviewEntity는 MemberEntity를 참조하고 있다.
    // 따라서 ReviewEntity를 생성하기 위해서는 MemberEntity가 필요하다.)
    MemberEntity memberEntity = memberRepository.findByUserId(authService.getCurrentUserId(authentication))
        .orElseThrow(()-> new AppException(ErrorCode.USERID_NOTFOUND, "유효하지 않은 사용자입니다."));

    // # 게시글 저장
    ReviewEntity result = reviewRepository.save(request.toEntity(memberEntity));

    // # 결과 반환
    return WriteReview.Response
        .builder()
        .reviewId(result.getReviewId())
        .title(result.getTitle())
        .content(result.getContent())
        .createdAt(result.getCreatedAt())
        .updatedAt(result.getUpdatedAt())
        .view(result.getView())
        .userId(result.getMember().getUserId())
        .name(result.getMember().getName())
        .build();

  }

  /** 게시글 수정 */
  public UpdateReview.Response updateReview(UpdateReview.Request request, Authentication authentication) {

    // # 수정하고자하는 게시글이 존재하는지 reviewId를 통해 찾기
    // !! 부가기능 리팩토링 할것
    ReviewEntity reviewEntity = reviewRepository.findByReviewId(request.getReviewId())
        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND,
            request.getReviewId() + " 해당 게시글은 존재하지 않습니다."));

    // # 수정할 게시글의 글쓴이와 현재 접속중인 유저의 아이디가 일치하는지 확인
    // !! 부가기능 리팩토링 할것
    String authorId = reviewEntity.getMember().getUserId();
    String curUserId = authService.getCurrentUserId(authentication);
    if(!authorId.equals(curUserId)){
      throw new AppException(ErrorCode.USERID_INVALID, "해당 게시글을 수정할 권한이 없습니다.");
    }

    // # 게시글 수정
    reviewEntity.setTitle(request.getTitle());
    reviewEntity.setContent(request.getContent());
    ReviewEntity result = reviewRepository.save(reviewEntity);

    // # 결과 반환
    return UpdateReview.Response
        .builder()
        .reviewId(result.getReviewId())
        .title(result.getTitle())
        .content(result.getContent())
        .createdAt(result.getCreatedAt())
        .updatedAt(LocalDateTime.now())
        .view(result.getView())
        .userId(result.getMember().getUserId())
        .name(result.getMember().getName())
        .build();
  }

  /** 게시글 삭제 */
  public void deleteReview(DeleteReview.Request request, Authentication authentication) {

    // # 삭제하고자하는 게시글이 존재하는지 reviewId를 통해 찾기
    // !! 리팩토링 할것
    ReviewEntity reviewEntity = reviewRepository.findByReviewId(request.getReviewId())
        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND,
            request.getReviewId() + " 해당 게시글은 존재하지 않습니다."));

    // # 삭제할 게시글의 글쓴이와 현재 접속중인 유저의 아이디가 일치하는지 확인
    // !! 부가기능 리팩토링 할것
    String authorId = reviewEntity.getMember().getUserId();
    String curUserId = authService.getCurrentUserId(authentication);
    if(!authorId.equals(curUserId)){
      throw new AppException(ErrorCode.USERID_INVALID, "해당 게시글을 삭제할 권한이 없습니다.");
    }

    // # 게시글 삭제
    reviewRepository.delete(reviewEntity);
  }

  /** 게시글 열람 (1개) */
  public Response readReview(ReadReview.Request request) {
    // # 삭제하고자하는 게시글이 존재하는지 reviewId를 통해 찾기
    // !! 리팩토링 할것
    ReviewEntity reviewEntity = reviewRepository.findByReviewId(request.getReviewId())
        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND,
            request.getReviewId() + " 해당 게시글은 존재하지 않습니다."));

    // # 조회수 1 증가
    // !! [문제점] 트렌젝션 관리를 어떻게 하면 좋을까?
    // 만약, 해당 게시글이 폭발적인 인기를 얻는 바람에
    // 수많은 사람들이 한꺼번에 하나의 게시글을 열람할 때,
    // 동시에 폭주하는 요청을 어떻게 처리할 것인가?
    reviewEntity.setView(reviewEntity.getView()+1);
    ReviewEntity result = reviewRepository.save(reviewEntity);

    // # 게시글 열람
    return ReadReview.Response
        .builder()
        .reviewId(result.getReviewId())
        .title(result.getTitle())
        .content(result.getContent())
        .createdAt(result.getCreatedAt())
        .updatedAt(LocalDateTime.now())
        .view(result.getView())
        .userId(result.getMember().getUserId())
        .name(result.getMember().getName())
        .build();
  }
}
