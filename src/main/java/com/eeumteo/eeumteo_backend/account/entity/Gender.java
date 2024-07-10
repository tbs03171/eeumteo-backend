package com.eeumteo.eeumteo_backend.account.entity;

public enum Gender {
    MALE, FEMALE;

    /**
     * String 을 Gender 로 변환해서 반환
     */
    public static Gender from(String s) {
        return Gender.valueOf(s.toUpperCase());
    }
}
