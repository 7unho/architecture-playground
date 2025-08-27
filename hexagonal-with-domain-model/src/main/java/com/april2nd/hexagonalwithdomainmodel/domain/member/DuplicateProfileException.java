package com.april2nd.hexagonalwithdomainmodel.domain.member;

public class DuplicateProfileException extends RuntimeException {
    public DuplicateProfileException(String message) {
        super(message);
    }
}
