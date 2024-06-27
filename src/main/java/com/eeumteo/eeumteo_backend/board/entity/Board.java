package com.eeumteo.eeumteo_backend.board.entity;

import com.eeumteo.eeumteo_backend.account.entity.Account;
import com.eeumteo.eeumteo_backend.account.entity.Profile;
import com.eeumteo.eeumteo_backend.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title; // 글 제목
    private String content; // 글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account writer; // 글 작성자
}
