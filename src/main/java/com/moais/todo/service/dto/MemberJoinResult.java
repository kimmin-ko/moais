package com.moais.todo.service.dto;

import com.moais.todo.domain.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@ToString
public class MemberJoinResult {

    private final Long id;
    private final String accountId;
    private final String nickname;
    private final LocalDateTime createdAt;

    public MemberJoinResult(Member member) {
        Assert.notNull(member,"Member must not be null.");

        this.id = member.getId();
        this.accountId = member.getAccountId();
        this.nickname = member.getNickname();
        this.createdAt = member.getCreatedAt();
    }
}
