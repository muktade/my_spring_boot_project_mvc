package com.myproject.test.myproject.dao;

import com.myproject.test.myproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);



    Optional<User> findByUserNameOrEmail(String userName, String userEmail);

    User findByUserName(String userName);

    @Query("SELECT u FROM User u WHERE u.userName = :userName or u.email = :email")
    User authUser(
            @Param("userName") String userName,
            @Param("email")  String email
    );

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isVerified = false , u.verifyCode = null, u.codeExpDate=null WHERE u.userName=?1")
    Integer deactive(String userName);

    @Transactional
    @Modifying
    Integer deleteByUserName(String userName);

}
