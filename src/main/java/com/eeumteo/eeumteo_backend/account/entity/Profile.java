package com.eeumteo.eeumteo_backend.account.entity;

import com.eeumteo.eeumteo_backend.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Profile extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String name;
    private LocalDate birthday;
}
