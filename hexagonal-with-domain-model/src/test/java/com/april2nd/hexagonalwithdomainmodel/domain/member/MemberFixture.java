package com.april2nd.hexagonalwithdomainmodel.domain.member;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * 회원 관련 테스트 객체 생성
 */
public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "april2nd", "long-secret");
    }

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("april2nd@test.com");
    }

    public static Member createMember() {
        return Member.register(createMemberRegisterRequest(), createPasswordEncoder(), createProfileAddressProvider());
    }

    public static Member createMember(Long memberId) {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder(), createProfileAddressProvider());
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Member createMember(String email) {
        return Member.register(createMemberRegisterRequest(email), createPasswordEncoder(), createProfileAddressProvider());
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

    public static ProfileAddressGenerator createProfileAddressProvider() {
        return () -> "welcome";
    }
}
