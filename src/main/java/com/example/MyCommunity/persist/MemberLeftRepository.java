package com.example.MyCommunity.persist;

import com.example.MyCommunity.persist.entity.MemberEntity;
import com.example.MyCommunity.persist.entity.MemberLeftEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** 탈퇴 회원 관련 Repository*/
public interface MemberLeftRepository extends JpaRepository<MemberLeftEntity, String> {
  Optional<MemberEntity> findByUserId(String userId);
}
