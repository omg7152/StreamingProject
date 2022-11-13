package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.dto.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String username);
}
