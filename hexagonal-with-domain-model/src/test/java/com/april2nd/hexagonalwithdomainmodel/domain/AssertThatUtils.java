package com.april2nd.hexagonalwithdomainmodel.domain;

import com.april2nd.hexagonalwithdomainmodel.domain.member.MemberRegisterRequest;
import org.assertj.core.api.AssertProvider;
import org.springframework.test.json.JsonPathValueAssert;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertThatUtils {
    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return memberId -> assertThat(memberId).isNotNull();
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(MemberRegisterRequest request) {
        return email -> email.assertThat().asString().isEqualTo(request.email());
    }
}
