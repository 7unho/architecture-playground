package com.april2nd.hexagonalwithdomainmodel.application.required;

import com.april2nd.hexagonalwithdomainmodel.domain.Email;
import com.april2nd.hexagonalwithdomainmodel.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);
}
