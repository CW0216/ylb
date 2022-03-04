package com.yhm.repository;

import com.yhm.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository {
    public Admin login(String username, String password);
}
