package com.april2nd.hexagonalwithdomainmodel.application.member.provided;

import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;
import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원 등록과 관련된 기능 제공
 */
public interface MemberRegister {
    // REVIEW: 엔티티를 상위계층에 전달하는게 적절한가 ?
    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);
}