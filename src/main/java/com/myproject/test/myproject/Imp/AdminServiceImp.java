package com.myproject.test.myproject.Imp;

import com.myproject.test.myproject.dao.AdminDao;
import com.myproject.test.myproject.entity.Admin;
import com.myproject.test.myproject.entity.User;
import com.myproject.test.myproject.service.AdminService;
import com.myproject.test.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserService userService;

//    @Autowired
//    private

    @Override
    public String register(Admin admin, User user, MultipartFile pht) {
        String status = alreadyExists(admin);
        if (status != null) {
            return status;
        }
        status = userService.registerUser(user, "ADMIN");
        if (!status.equals("1")) {
            return status;
        }

        String photo = pht.toString();
//        admin.setPhoto(pht);
        if(admin.getName()==null){
            admin.setName(admin.getUserName());
        }
        admin.setUserId(null);
        admin.setPhoto(null);
        admin.setIsActive(true);
        System.out.println("admin info: "+admin);
        adminDao.save(admin);
        return (photo != null) ? "1" : "0";


    }

    @Override
    public Admin update(Admin admin, MultipartFile photo) {
        Admin currentAdmin = adminDao.findByUserName(admin.getUserName());
        admin.setNationalId(currentAdmin.getNationalId());
        admin.setIsActive(currentAdmin.getIsActive());
        admin.setUserId(currentAdmin.getUserId());
        MultipartFile oldPhoto = currentAdmin.getPhoto();
        MultipartFile newPhoto = admin.getPhoto();
        if (newPhoto == null) {
            admin.setPhoto(currentAdmin.getPhoto());
        } else {
            admin.setPhoto(oldPhoto);
        }
        return adminDao.save(admin);
    }

    @Override
    public String updatePhoto(MultipartFile pht, String userName) {
        String photo = pht.toString();
        if (photo == null) {
            return "Photo not found";
        } else {
            String exitsPhoto = adminDao.findPhoto(userName);
            Integer count = adminDao.updateProfilePhoto(photo, userName);
            if (count == 1) {
                return "Photo Update Success";
            } else {
                return "Something is not Write";
            }
        }
    }

    @Override
    public Admin getByUserName(String userName) {
        return adminDao.findByUserName(userName);
    }

    private String alreadyExists(Admin admin) {
        if (adminDao.existsByNationalId(admin.getNationalId())) {
            return "National ID already exits";
        } else if (adminDao.existsByPhone(admin.getPhone())) {
            return "Phone is already exists";
        }
        return null;
    }
}
