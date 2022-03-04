package com.yhm.controller;


import com.yhm.repository.AdminRepository;
import com.yhm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminHandler {


    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login/{username}/{password}/{userType}")
    public Object login(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("userType") String userType){
        Object object=null;
        switch (userType){
            case "user":
                object =userRepository.login(username,password);
                break;
            case "admin":
                object =adminRepository.login(username,password);
                break;
        }
        return object;
    }
}
