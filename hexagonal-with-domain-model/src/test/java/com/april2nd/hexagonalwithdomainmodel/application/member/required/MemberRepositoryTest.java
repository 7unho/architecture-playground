package com.april2nd.hexagonalwithdomainmodel.application.member.required;

import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.april2nd.hexagonalwithdomainmodel.domain.member.MemberFixture.createMemberRegisterRequest;
import static com.april2nd.hexagonalwithdomainmodel.domain.member.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void createMember() {
        var member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
    }

    @Test
    void duplicateEmailFail() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member);

        Member member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> {
            memberRepository.save(member2);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}