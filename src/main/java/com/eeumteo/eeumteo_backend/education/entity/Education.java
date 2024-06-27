package com.eeumteo.eeumteo_backend.education.entity;

import com.eeumteo.eeumteo_backend.base.BaseEntity;
import com.eeumteo.eeumteo_backend.job.entity.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Education extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "education_id")
    private Long id;

    private String title; // 강좌명
    private LocalDateTime startDate; // 시작일
    private LocalDateTime endDate; // 종료일
    private int numberOfPositions; // 모집 인원
    private int numberOfApplicants; // 신청 인원

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "education_detail_id")
    private EducationDetail educationDetail;
}
