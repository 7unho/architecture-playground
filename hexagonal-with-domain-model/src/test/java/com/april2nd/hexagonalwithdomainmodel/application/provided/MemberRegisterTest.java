package com.april2nd.hexagonalwithdomainmodel.application.provided;

import com.april2nd.hexagonalwithdomainmodel.HexagonTestConfiguration;
import com.april2nd.hexagonalwithdomainmodel.domain.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(HexagonTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) -> junit-platform.properties로 대체
public record MemberRegisterTest(MemberRegister memberRegister) {
    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() ->
                memberRegister.register(MemberFixture.createMemberRegisterRequest())
        ).isInstanceOf(DuplicateEmailException.class);
    }
}