package com.example.MyCommunity.persist;

import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.persist.entity.ReviewEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
  Optional<ReviewEntity> findByReviewId(Integer reviewId);
}
