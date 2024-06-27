package com.eeumteo.eeumteo_backend.job.entity;

import com.eeumteo.eeumteo_backend.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JobDetail extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "job_detail_id")
    private Long id;

    private String contactPerson; // 담당자 이름
    private String contactInfo; // 담당자 연락처
    private int numberOfPositions; // 모집 인원
    private String description; // 상세 내용
    private String additionalInfo; // 기타 사항
    private String website; // 홈페이지
    private String address; // 주소
}
