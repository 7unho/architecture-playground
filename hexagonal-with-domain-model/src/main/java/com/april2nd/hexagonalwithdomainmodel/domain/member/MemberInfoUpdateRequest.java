package com.april2nd.hexagonalwithdomainmodel.domain.member;

public record MemberInfoUpdateRequest(
        String nickname,
        String profileAddress,
        String introduction
) {}
