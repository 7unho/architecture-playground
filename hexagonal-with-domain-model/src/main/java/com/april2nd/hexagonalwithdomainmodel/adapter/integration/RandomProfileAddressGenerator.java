package com.april2nd.hexagonalwithdomainmodel.adapter.integration;

import com.april2nd.hexagonalwithdomainmodel.domain.member.ProfileAddressGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomProfileAddressGenerator implements ProfileAddressGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
