package com.myproject.test.myproject.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
//@Entity

public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1234567898765432112L;
    @Column(name = "user_id", unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
}
