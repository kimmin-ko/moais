package com.moais.todo.service;

import com.moais.todo.domain.Member;
import com.moais.todo.persistence.MemberRepository;
import com.moais.todo.service.dto.MemberJoinCommand;
import com.moais.todo.service.dto.MemberJoinResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    String accountId = "account1234";
    String password = "password1234";
    String nickname = "nickname1234";

    @AfterEach
    void cleanup() {
        System.out.println("----- CLEANUP START -----");
        memberRepository.deleteAll();
        System.out.println("----- CLEANUP END -----");
    }

    @Test
    @DisplayName("회원가입이 정상 동작한다.")
    void join_test() {
        // given
        MemberJoinCommand command = new MemberJoinCommand(accountId, password, nickname);

        // when
        MemberJoinResult result = memberService.join(command);

        // then
        Optional<Member> targetOptional = memberRepository.findById(result.getId());
        assertThat(targetOptional).isPresent();

        Member target = targetOptional.get();
        assertThat(target.getAccountId()).isEqualTo(accountId);
        assertThat(passwordEncoder.matches(password, target.getPassword())).isTrue();
        assertThat(target.getNickname()).isEqualTo(nickname);
        assertThat(target.isWithdrawal()).isFalse();
        assertThat(target.getCreatedAt()).isNotNull();
        assertThat(target.getWithdrawalAt()).isNull();
    }

    @Test
    @DisplayName("회원가입 시 account id는 중복될 수 없다.")
    void exists_account_id_fail_test() {
        // given
        MemberJoinCommand memberJoinCommand = new MemberJoinCommand(accountId, password, nickname);
        memberService.join(memberJoinCommand);

        // when & then
        assertThatThrownBy(() -> memberService.join(memberJoinCommand))
                .as("Check the account id duplicate verification code.")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Account id already exists.");
    }

    @Test
    @DisplayName("회원가입 시 nickname은 중복될 수 없다.")
    void exists_nickname_fail_test() {
        // given
        memberService.join(new MemberJoinCommand(accountId, password, nickname));

        String notDuplicatedAccountId = "account4321";
        MemberJoinCommand duplicatedNicknameCommand = new MemberJoinCommand(notDuplicatedAccountId, password, nickname);

        // when & then
        assertThatThrownBy(() -> memberService.join(duplicatedNicknameCommand))
                .as("Check the nickname duplicate verification code.")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nickname already exists.");
    }

    @Test
    @DisplayName("회원 탈퇴가 정상적으로 동작한다.")
    void withdrawal_test() {
        // given
        Member member = new Member(accountId, passwordEncoder.encode(password), nickname);
        memberRepository.save(member);

        // when
        boolean result = memberService.withdrawal(member.getId(), password);

        // then
        assertThat(result).isTrue();

        Member target = memberService.getById(member.getId());
        assertThat(target.isWithdrawal()).isTrue();
        assertThat(target.getWithdrawalAt()).isNotNull();
    }

    @Test
    @DisplayName("이미 탈퇴한 회원은 탈퇴할 수 없다.")
    void withdrawal_fail_test() {
        // given
        Member member = new Member(accountId, passwordEncoder.encode(password), nickname);
        memberRepository.save(member);

        memberService.withdrawal(member.getId(), password);

        // when
        boolean result = memberService.withdrawal(member.getId(), password);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 회원탈퇴가 실패한다.")
    void withdrawal_password_not_match_fail_test() {
        // given
        Member member = new Member(accountId, password, nickname);
        memberRepository.save(member);

        // when & then
        assertThatThrownBy(() -> memberService.withdrawal(member.getId(), "invalid password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Passwords do not match.");
    }

}