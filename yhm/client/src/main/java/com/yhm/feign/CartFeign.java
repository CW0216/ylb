package com.yhm.feign;

import com.yhm.entity.Cart;
import com.yhm.entity.Medicine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "cart")
public interface CartFeign {
    @PostMapping("/cart/save")
    public void save(@RequestBody Cart cart);

    @GetMapping("/cart/count")
    public int count();

    @GetMapping("/cart/findAll/{index}/{limit}")
    public List<Cart> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit);

    @GetMapping("/cart/findAllById/{index}/{limit}/{uid}")
    public List<Cart> findAllById(@PathVariable("index") int index, @PathVariable("limit") int limit,@PathVariable("uid") long uid);

    @GetMapping("/cart/findAllByState/{index}/{limit}/{uid}/{state}")
    public List<Cart> findAllByState(@PathVariable("index") int index, @PathVariable("limit") int limit,@PathVariable("uid") long uid,@PathVariable("state") int state);

    @DeleteMapping("/cart/deleteById/{id}")
    public void deleteById(@PathVariable("id") long id);

    @PutMapping("/cart/update")
    public void update(@RequestBody Cart cart);

    @PutMapping("/cart/updateState/{id}/{state}")
    public void updateState(@PathVariable("id") long id,@PathVariable("state") int state);
}
