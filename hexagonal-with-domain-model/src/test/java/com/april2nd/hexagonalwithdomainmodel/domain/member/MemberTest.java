package com.april2nd.hexagonalwithdomainmodel.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.april2nd.hexagonalwithdomainmodel.domain.member.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;
    ProfileAddressGenerator profileAddressGenerator;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        this.profileAddressGenerator = createProfileAddressProvider();
        this.member = Member.register(createMemberRegisterRequest("test@test.com"), passwordEncoder, profileAddressGenerator);
    }

    @Test
    @DisplayName("사용자가 새로 생성될 때, PENDING 상태로 생성된다.")
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
        assertThat(member.getDetail().getProfile()).isNotNull();
    }

    @Test
    @DisplayName("생성자 호출 시, 필드에 Null이 들어오면 exception이 발생한다.")
    void constructorNullCheck() {
        var createRequest = createMemberRegisterRequest(null);
        assertThatThrownBy(() -> Member.register(createRequest, passwordEncoder, profileAddressGenerator))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("회원 활성화 시, 상태가 ACTIVE로 변경된다")
    void activate() {
        // when
        member.activate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void activateFail() {
        // when
        member.activate();

        // then
        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        // give
        member.activate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getDeactivatedAt()).isNull();

        // when
        member.deactivate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("long-secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("--", passwordEncoder)).isFalse();
    }

    @Test
    void changePassword() {
        member.changePassword("verySecret", passwordEncoder);

        assertThat(member.verifyPassword("verySecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();
        assertThat(member.isActive()).isTrue();

        member.deactivate();
        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        var createRequest = createMemberRegisterRequest("inval!d");

        assertThatThrownBy(() ->
                Member.register(createRequest, passwordEncoder, profileAddressGenerator)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest("test@test.com"), passwordEncoder, profileAddressGenerator);
    }

    @Test
    void updateInfo() {
        member.activate();

        MemberInfoUpdateRequest updateRequest = new MemberInfoUpdateRequest("april2nd", "april2nd", "자기소개");
        member.updateInfo(updateRequest);

        assertThat(member.getNickname()).isEqualTo(updateRequest.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(updateRequest.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(updateRequest.introduction());
    }

    @Test
    void updateInfoFail() {
        assertThatThrownBy(() ->
                member.updateInfo(new MemberInfoUpdateRequest("april2nd", "april2nd", "자기소개"))
        ).isInstanceOf(IllegalStateException.class);
    }
}