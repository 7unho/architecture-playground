package com.april2nd.hexagonalwithdomainmodel.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {
    @Test
    void profile() {
        new Profile("april2nd");
        new Profile("rlawnsgh8395");
        new Profile("1234551");
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("toolong".repeat(10))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("INVALID")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        Profile profile = new Profile("april2nd");

        assertThat(profile.url()).isEqualTo("@april2nd");
    }
}