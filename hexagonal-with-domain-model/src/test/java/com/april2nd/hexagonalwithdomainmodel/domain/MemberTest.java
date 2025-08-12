package com.april2nd.hexagonalwithdomainmodel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    @Test
    @DisplayName("사용자가 새로 생성될 때, PENDING 상태로 생성된다.")
    void createMember() {
        // give
        var member = new Member("test@test.com", "april2nd", "secret");
        // when
        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("생성자 호출 시, 필드에 Null이 들어오면 exception이 발생한다.")
    void constructorNullCheck() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Member(null, "april2nd", "secret"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("회원 활성화 시, 상태가 ACTIVE로 변경된다")
    void activate() {
        // given
        var member = new Member("test@test.com", "april2nd", "secret");

        // when
        member.activate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        // given
        var member = new Member("test@test.com", "april2nd", "secret");

        // when
        member.activate();

        // then
        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        // given
        var member = new Member("test@test.com", "april2nd", "secret");
        member.activate();

        // when
        member.deactivate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        var member = new Member("test@test.com", "april2nd", "secret");

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
}