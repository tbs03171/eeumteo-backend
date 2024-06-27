package com.eeumteo.eeumteo_backend.job.entity;

import com.eeumteo.eeumteo_backend.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Job extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "job_id")
    private Long id;

    private String title; // 공고명
    private String company; // 기업명
    private LocalDateTime startDate; // 시작일
    private LocalDateTime endDate; // 종료일
    private Boolean isClosed; // 마감 여부

    @Enumerated(value = EnumType.STRING)
    private ApplicationType applicationType; // 접수 방법

    @Enumerated(value = EnumType.STRING)
    private EmploymentType employmentType; // 고용 형태

    @Enumerated(value = EnumType.STRING)
    private JobCategory jobCategory; // 직종 (id, 이름)

    @Enumerated(value = EnumType.STRING)
    private Location location; // 근무지 (id, 이름)

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_detail_id")
    private JobDetail jobDetail;
}