package com.moais.todo.domain;

import com.moais.todo.domain.base.BaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.persistence.*;

@Slf4j
@Getter
@ToString(exclude = "member")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo")
@Entity
public class Todo extends BaseTimeEntity {

    private static final int MAX_LENGTH_OF_TITLE = 50;
    private static final int MAX_LENGTH_OF_CONTENT = 3000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    // constructor //
    public Todo(Member member, String title, String content) {
        Assert.notNull(member, "Member must not be null.");
        verifyTitle(title);
        verifyContent(content);

        this.member = member;
        this.title = title;
        this.content = content;
        this.status = TodoStatus.TO_DO;
    }

    // change //
    public void changeStatus(TodoStatus status) {
        if (status == null) {
            log.warn("status is null.");
            return;
        }
        this.status = status;
    }

    // getter //
    public Long getMemberId() {
        return this.member.getId();
    }

    // verify //
    private void verifyTitle(String title) {
        Assert.hasText(title, "Title must not be null or empty.");

        if (title.length() > MAX_LENGTH_OF_TITLE) {
            throw new IllegalArgumentException("Title must equal or less than 50 characters.");
        }
    }

    private void verifyContent(String content) {
        if (content.length() > MAX_LENGTH_OF_TITLE) {
            throw new IllegalArgumentException("Content must equal or less than 3000 characters.");
        }
    }
}
