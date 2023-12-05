package com.example.MyCommunity.persist;


import com.example.MyCommunity.persist.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** 회원 관련 Repository*/
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
  Optional<MemberEntity> findByUserId(String userId);
}
