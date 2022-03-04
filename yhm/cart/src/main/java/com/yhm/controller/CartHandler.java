package com.yhm.controller;

import com.yhm.entity.Cart;
import com.yhm.entity.User;
import com.yhm.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/cart")
@RestController
public class CartHandler {
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/save")
    public void save(@RequestBody Cart cart) {
        cart.setDate(new Date());
        cartRepository.save(cart);
    }

    @GetMapping("findAll/{index}/{limit}")
    public List<Cart> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit) {
        return cartRepository.findAll(index, limit);
    }

    @GetMapping("findAllById/{index}/{limit}/{uid}")
    public List<Cart> findAllById(@PathVariable("index") int index, @PathVariable("limit") int limit, @PathVariable("uid") long uid) {
        return cartRepository.findAllById(index, limit, uid);
    }

    @GetMapping("findAllByState/{index}/{limit}/{uid}/{state}")
    public List<Cart> findAllByState(@PathVariable("index") int index, @PathVariable("limit") int limit, @PathVariable("uid") long uid, @PathVariable("state") int state) {
        return cartRepository.findAllByState(index, limit, uid, state);
    }

    @GetMapping("/count")
    public int count() {
        return cartRepository.count();
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") long id){
        cartRepository.deleteById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Cart cart){
        cartRepository.update(cart);
    }

    @PutMapping("/updateState/{id}/{state}")
    public void updateState(@PathVariable("id") long id,@PathVariable("state") int state){
        cartRepository.updateState(id,state);
    }
}
