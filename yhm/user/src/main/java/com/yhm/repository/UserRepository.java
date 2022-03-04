package com.yhm.repository;

import com.yhm.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository {
    public List<User> findAll(int index, int limit);
    public User findById(long id);
    public int count();
    public void save(User user);
    public void update(User user);
    public void deleteById(long id);
}
