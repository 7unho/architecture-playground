package com.april2nd.hexagonalwithdomainmodel.application.member.required;

import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;
import com.april2nd.hexagonalwithdomainmodel.domain.member.Profile;
import com.april2nd.hexagonalwithdomainmodel.domain.shared.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query(
            value = "SELECT m " +
                    "FROM Member m " +
                    "WHERE m.detail.profile = :profile "
    )
    Optional<Member> findByProfile(Profile profile);
}
