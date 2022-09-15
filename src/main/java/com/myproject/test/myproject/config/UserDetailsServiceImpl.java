package com.myproject.test.myproject.config;

//import com.myproject.test.myproject.dao.UserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.myproject.test.myproject.custom.CustomeUserDetails;
import com.myproject.test.myproject.dao.UserDao;
import com.myproject.test.myproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user =userDao.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new CustomeUserDetails(user);
    }


//    @Autowired
//    private UserDao userDao;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//        return (UserDetails) userDao.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//    }
}
