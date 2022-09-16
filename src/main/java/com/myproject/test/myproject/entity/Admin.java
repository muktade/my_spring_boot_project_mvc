package com.myproject.test.myproject.entity;

import lombok.CustomLog;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
@Table(name = "admin")
public class Admin extends BaseEntity {
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "national_id", unique = true)
    private String nationalId;

    @Column (name="role", nullable = false)
    private String userRole;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "present_address",length = 65535, columnDefinition = "text")
    private String presentAddress;

    @Column(name = "permanent_address", length = 65535, columnDefinition = "text")
    private String permanentAddress;

    @Lob
    @Column(name = "photo", columnDefinition = "longblob")
    private MultipartFile photo;

    @Column(name = "is_active")
    private Boolean isActive;

}