package com.myproject.test.myproject.entity;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "user_name", nullable = false,insertable = false, updatable = false)
    private String userName;

    @Column(name = "user_email",nullable = false, unique = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_birth")
    private Date userBirth;

    @Lob
    @Column(name = "photo", columnDefinition = "longblob")
    private MultipartFile photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false,updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", nullable = false, updatable = false)})
    private Set<Role> roles = new HashSet<>(0);

    @Column(name = "is_verified")
    private  Boolean isVerified;

    @Column(name = "very_code")
    private String verifyCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "code_exp_date")
    private Date codeExpDate;

}
