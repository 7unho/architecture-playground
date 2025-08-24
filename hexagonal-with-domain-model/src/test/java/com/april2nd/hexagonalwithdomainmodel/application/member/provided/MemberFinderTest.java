package com.april2nd.hexagonalwithdomainmodel.application.member.provided;

import com.april2nd.hexagonalwithdomainmodel.HexagonTestConfiguration;
import com.april2nd.hexagonalwithdomainmodel.domain.member.Member;
import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(HexagonTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {
    @Test
    void find() {
        var member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member found = memberFinder.find(member.getId());

        assertThat(member.getId()).isEqualTo(found.getId());
    }

    @Test
    void findFail() {
        assertThatThrownBy(() -> {
            memberFinder.find(970402L);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}