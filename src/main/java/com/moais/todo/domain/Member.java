package com.moais.todo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String accountId;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    private boolean withdrawal;

    private LocalDateTime withdrawalAt;

}
