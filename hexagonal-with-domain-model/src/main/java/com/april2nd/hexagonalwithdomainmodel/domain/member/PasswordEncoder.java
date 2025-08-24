package com.april2nd.hexagonalwithdomainmodel.domain.member;

public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String passwordHash);
}
