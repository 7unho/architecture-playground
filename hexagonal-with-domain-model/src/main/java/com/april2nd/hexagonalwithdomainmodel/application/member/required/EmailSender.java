package com.april2nd.hexagonalwithdomainmodel.application.member.required;

import com.april2nd.hexagonalwithdomainmodel.domain.shared.Email;

/**
 * 메일 발송 기능 제공
 */

public interface EmailSender {
    void send(Email email, String subject, String body);
}
