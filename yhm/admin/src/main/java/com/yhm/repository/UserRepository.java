package com.yhm.repository;


import com.yhm.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    public User login(String username, String password);
}
