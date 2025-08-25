package com.april2nd.hexagonalwithdomainmodel.domain.member;

import com.april2nd.hexagonalwithdomainmodel.domain.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.util.Assert.isTrue;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {
    private Profile profile;

    private String introduction;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    static MemberDetail create(String profileAddress) {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.profile = new Profile(profileAddress);
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    public void activate() {
        isTrue(activatedAt == null, "[MemberDetail.activate] 이미 activatedAt이 설정 되어 있습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        isTrue(deactivatedAt == null, "[MemberDetail.deactivate] 이미 deactivatedAt이 설정 되어 있습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
