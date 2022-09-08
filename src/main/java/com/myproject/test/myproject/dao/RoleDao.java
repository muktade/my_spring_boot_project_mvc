package com.myproject.test.myproject.dao;

import com.myproject.test.myproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleDao extends JpaRepository<Role, Long> {

    Set<Role> findByName(String name);
}
