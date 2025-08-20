package com.april2nd.hexagonalwithdomainmodel;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class HexagonalWithDomainModelApplicationTest {
    @Test
    void main() {
        MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class);

        HexagonalWithDomainModelApplication.main(new String[0]);

        mocked.verify(() -> SpringApplication.run(HexagonalWithDomainModelApplication.class, new String[0]));
    }
}