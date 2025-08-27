package com.april2nd.hexagonalwithdomainmodel.adapter.webapi.dto;

import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;

public record MemberRegisterResponse(
        Long memberId,
        String emailAddress
) {
    public static MemberRegisterResponse of(Member member) {
        return new MemberRegisterResponse(
                member.getId(),
                member.getEmail().address()
        );
    }
}
