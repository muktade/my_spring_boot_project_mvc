package com.myproject.test.myproject.dao;

import com.myproject.test.myproject.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {

    Admin findByUserName(String UserName);

    @Query("SELECT a.photo FROM Admin a where a.userName=:userName")
    String findPhoto(@Param("userName") String userName);

    boolean existsByUserName(String userName);

    boolean existsByPhone(String phone);

    boolean existsByNationalId(String nationalId);

    @Transactional
    @Modifying
    @Query("UPDATE Admin a SET a.photo = ?1 WHERE a.userName = ?2")
    Integer updateProfilePhoto(String photo, String userName);

    @Transactional
    @Modifying
    @Query("delete FROM Admin a WHERE a.userName= ?1")
    Integer delete(String userName);
}
