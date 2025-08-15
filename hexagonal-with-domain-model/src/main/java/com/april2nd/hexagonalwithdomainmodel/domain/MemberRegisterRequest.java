package com.april2nd.hexagonalwithdomainmodel.domain;

public record MemberRegisterRequest(
        String email,
        String nickname,
        String password
) {
}
