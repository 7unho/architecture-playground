package com.april2nd.hexagonalwithdomainmodel;

import com.april2nd.hexagonalwithdomainmodel.application.member.required.EmailSender;
import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberFixture;
import com.april2nd.hexagonalwithdomainmodel.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class HexagonTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, content) -> System.out.println(String.format("[Test Email Sender: email: %s, subject: %s, content: %s", email.address(), subject, content));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
