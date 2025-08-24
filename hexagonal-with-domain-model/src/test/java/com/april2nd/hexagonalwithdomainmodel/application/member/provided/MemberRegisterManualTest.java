package com.april2nd.hexagonalwithdomainmodel.application.member.provided;

import com.april2nd.hexagonalwithdomainmodel.application.member.MemberModifyService;
import com.april2nd.hexagonalwithdomainmodel.application.member.required.EmailSender;
import com.april2nd.hexagonalwithdomainmodel.application.member.required.MemberRepository;
import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberFixture;
import com.april2nd.hexagonalwithdomainmodel.domain.shared.Email;
import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;
import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class MemberRegisterManualTest {
    @Test
    void registerTestWithStub() {
        MemberRegister register = new MemberModifyService(
                new FakeMemberRepositoryStub(),
                new EmailSenderStub(),
                MemberFixture.createPasswordEncoder(),
                new FakeMemberFinderStub()
                );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void registerTestWithMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();

        MemberRegister register = new MemberModifyService(
                new FakeMemberRepositoryStub(),
                emailSenderMock,
                MemberFixture.createPasswordEncoder(),
                new FakeMemberFinderStub()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        assertThat(emailSenderMock.getTos()).hasSize(1);
        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
    }

    @Test
    void registerTestWithMockito() {
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

        MemberRegister register = new MemberModifyService(
                new FakeMemberRepositoryStub(),
                emailSenderMock,
                MemberFixture.createPasswordEncoder(),
                new FakeMemberFinderStub()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
    }

    static class FakeMemberFinderStub implements MemberFinder {
        @Override
        public Member find(Long memberId) {
            return null;
        }
    }

    static class FakeMemberRepositoryStub implements MemberRepository {
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findById(Long memberId) {
            return Optional.empty();
        }
    }

    static class EmailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String body) {
            System.out.println("[MemberRegisterManualTest.EmailSenderStub] send email: " + email.address());
        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }

        public List<Email> getTos() {
            return this.tos;
        }
    }
}