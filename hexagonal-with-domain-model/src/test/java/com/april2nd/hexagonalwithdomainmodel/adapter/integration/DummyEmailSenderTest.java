package com.april2nd.hexagonalwithdomainmodel.adapter.integration;

import com.april2nd.hexagonalwithdomainmodel.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {
    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        Email email = new Email("april2nd@test.com");
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(email, "subject", "body");

        assertThat(out.capturedLines()[0]).isEqualTo(String.format("[DummyEmailSender] send email : ") + email);
    }
}