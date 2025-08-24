package com.april2nd.hexagonalwithdomainmodel.domain.member;

import java.util.regex.Pattern;

public record Profile(String address) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[a-z0-9]+");

    public Profile {
        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("[Profile.constructor] 프로필 주소 형식이 바르지 않습니다. " + address);
        }

        if (address.length() > 15) throw new IllegalArgumentException("[Profile.constructor] 프로필 주소는 최대 15자리를 넘을 수 없습니다.");
    }

    public String url() {
        return "@" + address;
    }
}
