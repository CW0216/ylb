package com.yhm.repository;

import com.yhm.entity.Cart;
import com.yhm.entity.Medicine;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository {
    public void save(Cart cart);
    public List<Cart> findAll(int index,int limit);
    public int count();
    public List<Cart> findAllById(int index,int limit,long uid);
    public List<Cart> findAllByState(int index,int limit,long uid,int state);
    public void deleteById(long id);
    public void update(Cart cart);
    public void updateState(long id,int state);
}
