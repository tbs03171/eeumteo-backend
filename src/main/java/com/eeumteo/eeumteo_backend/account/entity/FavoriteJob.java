package com.eeumteo.eeumteo_backend.account.entity;

import com.eeumteo.eeumteo_backend.base.BaseEntity;
import com.eeumteo.eeumteo_backend.job.entity.Job;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.Jar;

@Entity
@Getter
@NoArgsConstructor
public class FavoriteJob extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "favorite_job_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
}
