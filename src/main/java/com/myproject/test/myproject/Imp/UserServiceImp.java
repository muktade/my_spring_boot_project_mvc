package com.myproject.test.myproject.Imp;

import com.myproject.test.myproject.dao.RoleDao;
import com.myproject.test.myproject.dao.UserDao;
import com.myproject.test.myproject.entity.Role;
import com.myproject.test.myproject.entity.User;
import com.myproject.test.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String registerUser(User user, String roleName) {
        String status = this.alreadyExists(user);
        if(status !=null){
            return status;
        }
        setUserFinalVale(user, roleName);
        userDao.save(user);
        return "1";
    }

    @Override
    public String updateUser(User newUser, String oldRawPassword) {
        User oldUser = userDao.findByUserName(getUserName());
        String email = newUser.getEmail();
        if(email !=null && !email.isEmpty()&& !email.equals(oldUser.getEmail())){
            boolean emailExits = userDao.existsByEmail(email);
            if(emailExits){
                return "00";
            }
            oldUser.setEmail(email);
        }
        String password = newUser.getUserPassword();
        if(password !=null &&!password.isEmpty()){
            boolean validPassword=bCryptPasswordEncoder.matches(oldRawPassword ,oldUser.getUserPassword());
            if(validPassword){
                oldUser.setUserPassword(bCryptPasswordEncoder.encode(password));
            }else{
                return "o1";
            }
        }
        userDao.save(oldUser);
        return  "11";
    }

    @Override
    public User findByUserName(String userName) {

        return userDao.findByUserName(userName);
    }

    @Override
    public String getUserName() {

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Integer delete(String userName) {

        return userDao.deleteByUserName(userName);
    }

    @Override
    public Integer deactivate(String userName) {

        return userDao.deactive(userName);
    }

    private String alreadyExists(User user){
        if(userDao.existsByUserName(user.getUserName())){
            return "User already Exists";
        } else if(userDao.existsByEmail(user.getEmail())) {
            return "Email already exists";
        }
        return null;
    }

    public  void setUserFinalVale(User user, String roleName){
        String vCode= (int) (Math.random()*1000000)+"";
        Set<Role> roles= getRolesByName(roleName);
        Date newDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        c.add(Calendar.DATE, 3);
        Date expDate= c.getTime();
        String password = bCryptPasswordEncoder.encode(user.getUserPassword());
        user.setRoles(roles);
        user.setUserPassword(password);
        user.setVerifyCode(vCode);
        user.setCodeExpDate(expDate);
        if(roleName.equals("ADMIN")){
            user.setIsVerified(true);
        }else {
            user.setIsVerified(true);
        }
    }

    private Set<Role> getRolesByName(String roleName){
        Set<Role> roles = roleDao.findByName(roleName);
        if(roles.isEmpty()){
            Role role = roleDao.save(new Role(roleName));
            return (role != null) ? getRolesByName(roleName):null;
        }
        return roles;
    }
}
