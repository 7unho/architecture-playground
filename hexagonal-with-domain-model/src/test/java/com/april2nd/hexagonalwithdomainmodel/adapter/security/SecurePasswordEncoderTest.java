package com.april2nd.hexagonalwithdomainmodel.adapter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecurePasswordEncoderTest {
    @Test
    void securePasswordEncoder() {
        SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();

        String secret = securePasswordEncoder.encode("secret");

        assertThat(securePasswordEncoder.matches("secret", secret)).isTrue();
        assertThat(securePasswordEncoder.matches("sec2ret", secret)).isFalse();
    }
}