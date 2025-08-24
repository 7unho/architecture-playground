package com.april2nd.hexagonalwithdomainmodel.application.member;

import com.april2nd.hexagonalwithdomainmodel.application.member.provided.MemberFinder;
import com.april2nd.hexagonalwithdomainmodel.application.member.required.MemberRepository;
import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("[MemberService.activate] 회원을 찾을 수 없습니다. ID : " + memberId));
    }
}
