package com.yhm.controller;

import com.yhm.entity.Cart;
import com.yhm.entity.Medicine;
import com.yhm.entity.User;
import com.yhm.tools.CartResult;
import com.yhm.tools.Result;
import com.yhm.feign.CartFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartHandler {
    @Autowired
    private CartFeign cartFeign;

    @RequestMapping("/save")
    public Result save(@RequestBody Map<String, String> map) {
        Result result = new Result();
        Cart cart = new Cart();
        User user=new User();
        Medicine medicine = new Medicine();
        medicine.setId(Long.parseLong(map.get("mid")));
        long uid=Long.parseLong(map.get("uid"));
        user.setId(uid);
        cart.setUser(user);
        cart.setMedicine(medicine);
        cart.setDate(new Date());
        cart.setState(0);
        cartFeign.save(cart);
        return result;
    }

    @RequestMapping("findAll")
    public CartResult findAll(@RequestParam("page") int page, @RequestParam("limit")int limit) {
        int index=(page-1)*limit;
        CartResult cartResult = new CartResult();
        cartResult.setCode(0);
        cartResult.setMsg("成功");
        cartResult.setCount(cartFeign.count());
        cartResult.setData(cartFeign.findAll(index, limit));
        return cartResult;
    }

    @RequestMapping("findAllById")
    public CartResult findAllById(@RequestParam("page") int page, @RequestParam("limit")int limit, long uid) {
        int index=(page-1)*limit;
        CartResult cartResult = new CartResult();
        cartResult.setCount(cartFeign.count());
        cartResult.setData(cartFeign.findAllById(index, limit, uid));
        return cartResult;
    }

    @RequestMapping("findAllByState")
    public CartResult findAllByState(@RequestParam("page") int page, @RequestParam("limit")int limit, long uid,int state) {
        int index=(page-1)*limit;
        CartResult cartResult = new CartResult();
        cartResult.setCount(cartFeign.count());
        cartResult.setData(cartFeign.findAllByState(index, limit, uid, state));
        System.out.println(cartResult);
        return cartResult;
    }

    @RequestMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") long id) {

        cartFeign.deleteById(id);
    }
    @PutMapping("/update")
    public void  update(@RequestBody Cart cart){
        cartFeign.update(cart);
    }

    @RequestMapping("/updateState")
    public void  updateState(@RequestBody Map<String, String> map){
        long id =Long.parseLong(map.get("id"));
        int state=Integer.parseInt(map.get("state"));
        cartFeign.updateState(id,state);
    }
}
