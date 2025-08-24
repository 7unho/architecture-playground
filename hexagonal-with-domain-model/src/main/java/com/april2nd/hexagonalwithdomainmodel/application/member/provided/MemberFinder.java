package com.april2nd.hexagonalwithdomainmodel.application.member.provided;

import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;

/**
 * 회원을 조회 관련 기능 제공
 */

public interface MemberFinder {
    Member find(Long memberId);
}
