package com.yhm.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "admin")
public interface AdminFeign {

    @GetMapping("/admin/login/{username}/{password}/{userType}")
    public Object login(@PathVariable String username, @PathVariable String password, @PathVariable String userType);
}
