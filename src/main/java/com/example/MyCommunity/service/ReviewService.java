package com.example.MyCommunity.service;

import com.example.MyCommunity.dto.reviewDto.Review;
import com.example.MyCommunity.exception.AppException;
import com.example.MyCommunity.exception.ErrorCode;
import com.example.MyCommunity.persist.MemberRepository;
import com.example.MyCommunity.persist.ReviewRepository;
import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.persist.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;

  /** 게시글 작성 */
  public Review.Response writeReview(Review.Request request, Authentication authentication) throws UsernameNotFoundException  {

    // # 현재 사용자의 엔티티를 Authentication과 UserDetails를 이용하여 가져온다.
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String userId = userDetails.getUsername();
    MemberEntity memberEntity = memberRepository.findByUserId(userId)
        .orElseThrow(()-> new AppException(ErrorCode.USERID_NOTFOUND, "유효하지 않은 사용자입니다."));

    // # 게시글 저장 및 반환
    ReviewEntity result = reviewRepository.save(request.toEntity(memberEntity));
    return getResponse(result);

  }

  /** 게시글이 성공적으로 올라온 결과를 반환하는 메서드*/
  private Review.Response getResponse(ReviewEntity result){
    return Review.Response
        .builder()
        .reviewId(result.getReviewId())
        .title(result.getTitle())
        .content(result.getContent())
        .createdAt(result.getCreatedAt())
        .updatedAt(result.getUpdatedAt())
        .view(result.getView())
        .userId(result.getMember().getUserId())
        .build();
  }
}
