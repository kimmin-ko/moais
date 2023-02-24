package com.moais.todo.domain;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    private static final int MAX_LENGTH_OF_ACCOUNT_ID = 30;
    private static final int MAX_LENGTH_OF_ENCRYPTED_PASSWORD = 100;
    private static final int MAX_LENGTH_OF_NICKNAME = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String accountId;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    private boolean withdrawal;

    private LocalDateTime withdrawalAt;

    // constructor //
    public Member(String accountId, String password, String nickname) {
        verifyAccountId(accountId);
        verifyPassword(password);
        verifyNickname(nickname);

        this.accountId = accountId;
        this.password = password;
        this.nickname = nickname;
    }

    // verify //
    private void verifyAccountId(String accountId) {
        Assert.hasText(accountId, "Account id must not be null or empty.");

        if (accountId.length() > MAX_LENGTH_OF_ACCOUNT_ID) {
            throw new IllegalArgumentException("Account id must equal or less than 30 characters.");
        }
    }

    private void verifyPassword(String password) {
        Assert.hasText(password, "Password must not be null or empty.");

        if (password.length() > MAX_LENGTH_OF_ENCRYPTED_PASSWORD) {
            throw new IllegalArgumentException("Password must equal or less than 100 characters.");
        }
    }

    private void verifyNickname(String nickname) {
        Assert.hasText(nickname, "Nickname must not be null or empty.");

        if (nickname.length() > MAX_LENGTH_OF_NICKNAME) {
            throw new IllegalArgumentException("Nickname must equal or less than 20 characters.");
        }
    }
}
