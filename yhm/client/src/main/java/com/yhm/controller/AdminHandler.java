package com.yhm.controller;

import com.yhm.entity.Admin;
import com.yhm.tools.Result;
import com.yhm.entity.User;
import com.yhm.tools.UserResult;
import com.yhm.feign.AdminFeign;
import com.yhm.tools.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminHandler {

    @Autowired
    private AdminFeign adminFeign;
    Encryption encryption=new Encryption();

    /**
     *用户和管理员登录
     */
    @RequestMapping("/login")
    public Result login(@RequestBody Map<String, String> map){
        String username=map.get("username");
        String password=map.get("password");
        String userType=map.get("userType");
        password=encryption.encryptionPsw(password);
        Object object=adminFeign.login(username,password,userType);//{username:"",passwrod:"",userType:"user"}
        LinkedHashMap<String,Object> hashMap=(LinkedHashMap)object;
        Result result = new Result();
        System.out.println(hashMap);
        if(object==null){
            result.setCode(0);
            result.setMsg("登录失败");
            return result;
        }
        else {
            switch (userType){
                case "user":
                    UserResult userResult=new UserResult();
                    User user=new User();
                    String nickname=(String)hashMap.get("nickname");
                    user.setNickname(nickname);
                    int id=(int)hashMap.get("id");
                    user.setId((long)id);
                    userResult.setCode(1);
                    userResult.setMsg("用户登录成功");
                    userResult.setUser(user);
                    userResult.setUid(user.getId());
                    System.out.println(userResult);
                    return userResult;
                case "admin":
                    Admin admin=new Admin();
                    result.setCode(2);
                    result.setMsg("管理员登录成功");
                    break;
            }
        }
        return result;
    }
}
