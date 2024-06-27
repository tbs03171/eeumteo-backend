package com.eeumteo.eeumteo_backend.education.entity;


import com.eeumteo.eeumteo_backend.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EducationDetail extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "education_detail_id")
    private Long id;

    private String description; // 상세 내용
    private LocalDateTime startDate; // 강좌 시작일
    private LocalDateTime endDate; // 강좌 종료일
    private String applicationType; // 신청 방법
    private int time; // 교육 시간
}
