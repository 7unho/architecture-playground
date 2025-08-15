package com.april2nd.hexagonalwithdomainmodel.application.required;

import com.april2nd.hexagonalwithdomainmodel.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.april2nd.hexagonalwithdomainmodel.domain.MemberFixture.createMemberRegisterRequest;
import static com.april2nd.hexagonalwithdomainmodel.domain.MemberFixture.createPasswordEncoder;
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
}