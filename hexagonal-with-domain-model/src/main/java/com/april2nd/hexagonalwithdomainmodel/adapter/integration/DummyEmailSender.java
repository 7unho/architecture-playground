package com.april2nd.hexagonalwithdomainmodel.adapter.integration;

import com.april2nd.hexagonalwithdomainmodel.application.member.required.EmailSender;
import com.april2nd.hexagonalwithdomainmodel.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("[DummyEmailSender] send email : " + email);
    }
}
