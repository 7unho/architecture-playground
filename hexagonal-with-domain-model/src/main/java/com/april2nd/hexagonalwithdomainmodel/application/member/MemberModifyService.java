package com.april2nd.hexagonalwithdomainmodel.application.member;

import com.april2nd.hexagonalwithdomainmodel.application.member.provided.MemberFinder;
import com.april2nd.hexagonalwithdomainmodel.application.member.provided.MemberRegister;
import com.april2nd.hexagonalwithdomainmodel.application.member.required.EmailSender;
import com.april2nd.hexagonalwithdomainmodel.application.member.required.MemberRepository;
import com.april2nd.hexagonalwithdomainmodel.domain.member.DuplicateEmailException;
import com.april2nd.hexagonalwithdomainmodel.domain.shared.Email;
import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;
import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberRegisterRequest;
import com.april2nd.hexagonalwithdomainmodel.domain.member.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberFinder memberFinder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        /**
         * MEMO: 일반적인 JpaRepository를 사용하는 경우, 영속성 객체의 변경이 일어날 경우, 자동으로 반영되지만 Repository를 사용하고 있기 때문에, save를 호출해야 함
         *
         * Spring Data에서 업데이트 상황에서 Save를 호출해야 하는 2가지 이유
         * 1. Spring Data는 Jpa를 위한 기술이 아니다.
         * - NoSql, RDB 등 다양한 저장기술을 위한 추상화 인터페이스 이므로, Jpa에서도 save를 호출해야 함
         * 2. Spring Data가 제공하는 Domain Event Publication을 사용할 경우, 업데이트 시에도 save를 호출해야 함
         */
        return memberRepository.save(member);
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
