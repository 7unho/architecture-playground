package com.april2nd.hexagonalwithdomainmodel.application.required;

import com.april2nd.hexagonalwithdomainmodel.domain.Member;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
}
