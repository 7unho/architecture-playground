package com.april2nd.hexagonalwithdomainmodel.application;

import com.april2nd.hexagonalwithdomainmodel.application.provided.MemberRegister;
import com.april2nd.hexagonalwithdomainmodel.application.required.EmailSender;
import com.april2nd.hexagonalwithdomainmodel.application.required.MemberRepository;
import com.april2nd.hexagonalwithdomainmodel.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("[MemberService.register] 이미 사용 중인 이메일입니다: " + registerRequest.email());
        }
    }
}
