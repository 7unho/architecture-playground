package com.april2nd.hexagonalwithdomainmodel.application.provided;

import com.april2nd.hexagonalwithdomainmodel.domain.Member;

/**
 * 회원을 조회 관련 기능 제공
 */

public interface MemberFinder {
    Member find(Long memberId);
}
