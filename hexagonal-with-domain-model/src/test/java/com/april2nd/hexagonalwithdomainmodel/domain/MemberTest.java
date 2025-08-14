package com.april2nd.hexagonalwithdomainmodel.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
        this.member = Member.create(new MemberCreateRequest("test@test.com", "april2nd", "secret"), passwordEncoder);
    }

    @Test
    @DisplayName("사용자가 새로 생성될 때, PENDING 상태로 생성된다.")
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("생성자 호출 시, 필드에 Null이 들어오면 exception이 발생한다.")
    void constructorNullCheck() {
        assertThatThrownBy(() -> Member.create(new MemberCreateRequest(null, "april2nd", "secret"), passwordEncoder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("회원 활성화 시, 상태가 ACTIVE로 변경된다")
    void activate() {
        // when
        member.activate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
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

        // when
        member.deactivate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassowrd() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("invalid", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("april2nd");

        String newNickname = "updated";
        member.changeNickname(newNickname);

        assertThat(member.getNickname()).isEqualTo(newNickname);
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
}