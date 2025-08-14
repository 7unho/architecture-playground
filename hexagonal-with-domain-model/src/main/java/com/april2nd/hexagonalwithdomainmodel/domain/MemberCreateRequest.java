package com.april2nd.hexagonalwithdomainmodel.domain;

public record MemberCreateRequest(
        String email,
        String nickname,
        String password
) {
}
