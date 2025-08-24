package com.april2nd.hexagonalwithdomainmodel.application.member.provided;

import com.april2nd.hexagonalwithdomainmodel.HexagonTestConfiguration;
import com.april2nd.hexagonalwithdomainmodel.domain.member.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(HexagonTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) -> junit-platform.properties로 대체
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {
    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() ->
                memberRegister.register(MemberFixture.createMemberRegisterRequest())
        ).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("test@test.com", "april2nd", "secret"));
        checkValidation(new MemberRegisterRequest("test@test.com", "april2ndapril2ndapril2ndapril2ndapril2nd", "long-secret"));
        checkValidation(new MemberRegisterRequest("invali!demail", "april2nd", "long-secret"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() ->
            memberRegister.register(invalid)
        ).isInstanceOf(ConstraintViolationException.class);
    }
}