package com.april2nd.hexagonalwithdomainmodel.adapter.webapi;

import com.april2nd.hexagonalwithdomainmodel.adapter.webapi.dto.MemberRegisterResponse;
import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

class MemberApiTest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void register() {
        MemberRegisterRequest request = new MemberRegisterRequest(
                "april2nd@test.com",
                "april2nd",
                "long-secret"
        );

        MemberRegisterResponse response = register(request);

        assertThat(response.emailAddress()).isEqualTo("april2nd@test.com");
    }

    private MemberRegisterResponse register(MemberRegisterRequest request) {
        return restClient.post()
                .uri("/api/v1/members")
                .body(request)
                .retrieve()
                .body(MemberRegisterResponse.class);
    }
}