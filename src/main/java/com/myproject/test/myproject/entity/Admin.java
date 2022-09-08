package com.myproject.test.myproject.entity;

import lombok.CustomLog;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "admin")
public class Admin extends BaseEntity {
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "national_id", unique = true)
    private String nationalId;

    @Column(name = "present_address",length = 65535, columnDefinition = "text")
    private String presentAddress;

    @Column(name = "permanent_address", length = 65535, columnDefinition = "text")
    private String permanentAddress;

    @Lob
    @Column(name = "photo", columnDefinition = "longblob")
    private String photo;

    @Column(name = "is_active")
    private Boolean isActive;

}