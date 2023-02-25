package com.moais.todo.domain;

import com.moais.todo.util.StringGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    String accountId = "account1234";
    String password = "password1234";
    String nickname = "nickname1234";

    @Test
    @DisplayName("Member 객체를 정상적으로 생성한다.")
    void create_member_test() {
        // given
        String accountId = StringGenerator.generate(30);
        String password = StringGenerator.generate(100);
        String nickname = StringGenerator.generate(20);

        // when
        Member target = new Member(accountId, password, nickname);

        // then
        assertThat(target.getId()).isNull();
        assertThat(target.getAccountId()).isEqualTo(accountId);
        assertThat(target.getPassword()).isEqualTo(password);
        assertThat(target.getNickname()).isEqualTo(nickname);
        assertThat(target.isWithdrawal()).isFalse();
        assertThat(target.getWithdrawalAt()).isNull();
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("getInvalidAccountId")
    @DisplayName("회원의 계정 ID는 필수 값이고, 30자 이하여야 한다.")
    void verify_account_id_test(String invalidAccountId) {
        // when & then
        assertThatThrownBy(() -> new Member(invalidAccountId, password, nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("getInvalidPassword")
    @DisplayName("회원의 비밀번호는 필수 값이고, 100자 이하여야 한다.")
    void verify_password_test(String invalidPassword) {
        // when & then
        assertThatThrownBy(() -> new Member(accountId, invalidPassword, nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("getInvalidNickname")
    @DisplayName("회원의 닉네임은 필수 값이고, 20자 이하여야 한다.")
    void verify_nickname_test(String invalidNickname) {
        // when & then
        assertThatThrownBy(() -> new Member(accountId, password, invalidNickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("회원 탈퇴가 정상적으로 동작한다.")
    void withdrawal_test() {
        // given
        Member member = new Member(accountId, password, nickname);

        // when & then
        assertThat(member.isWithdrawal()).isFalse();
        assertThat(member.getWithdrawalAt()).isNull();

        member.withdrawal();
        assertThat(member.isWithdrawal()).isTrue();
        assertThat(member.getWithdrawalAt()).isNotNull();
    }

    // private //
    private static Stream<Arguments> getInvalidAccountId() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(StringGenerator.generate(31))
        );
    }

    private static Stream<Arguments> getInvalidPassword() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(StringGenerator.generate(101))
        );
    }

    private static Stream<Arguments> getInvalidNickname() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(StringGenerator.generate(21))
        );
    }
}