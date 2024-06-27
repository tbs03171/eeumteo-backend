package com.eeumteo.eeumteo_backend.account.entity;

import com.eeumteo.eeumteo_backend.base.BaseEntity;
import com.eeumteo.eeumteo_backend.education.entity.Education;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FavoriteEducation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "favorite_education_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_id")
    private Education education;
}
