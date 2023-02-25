package com.moais.todo.domain;

import com.moais.todo.util.StringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TodoTest {

    Member writer;
    String content = "content1234";

    @BeforeEach
    void setup() {
        System.out.println("----- SETUP START -----");
        String accountId = "accountId1234";
        String password = "password1234";
        String nickname = "nickname1234";
        writer = new Member(accountId, password, nickname);
        System.out.println("----- SETUP END -----");
    }

    @Test
    @DisplayName("TODO를 정상적으로 생성한다.")
    void create_todo() {
        // given
        String title = StringGenerator.generate(50);

        // when
        Todo target = new Todo(writer, title, content);

        // then
        assertThat(target.getId()).isNull();
        assertThat(target.getMember()).isEqualTo(writer);
        assertThat(target.getTitle()).isEqualTo(title);
        assertThat(target.getContent()).isEqualTo(content);
        assertThat(target.getStatus()).isEqualTo(TodoStatus.TO_DO);
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("getInvalidTitle")
    @DisplayName("TODO의 제목은 필수 값이고, 50자 이하여야 한다.")
    void verify_title_test(String invalidTitle) {
        // when & then
        assertThatThrownBy(() -> new Todo(writer, invalidTitle, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title must");
    }

    // private //
    private static Stream<Arguments> getInvalidTitle() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(StringGenerator.generate(51))
        );
    }

}