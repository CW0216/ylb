package com.yhm.controller;

import com.yhm.tools.Result;
import com.yhm.entity.User;
import com.yhm.tools.UsersResult;
import com.yhm.feign.UserFeign;
import com.yhm.tools.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserHandler {
    @Autowired
    private UserFeign userFeign;
    Encryption encryption=new Encryption();
    @RequestMapping("/findAll")
    public UsersResult findAll(@RequestParam("page") int page, @RequestParam("limit")int limit){
        int index=(page-1)*limit;
        UsersResult usersResult = new UsersResult();
        usersResult.setCode(0);
        usersResult.setMsg("成功");
        usersResult.setCount(userFeign.count());
        usersResult.setData(userFeign.findAll(index,limit));
        return usersResult;
    }

    @GetMapping("findById/{id}")
    public User findById(@PathVariable("id") long id){
        return userFeign.findById(id);
    }

    @GetMapping("/count")
    public int count(){
        return userFeign.count();
    }

    /**
     *用户注册
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Map<String, String> map){
        Result result=new Result();
        User user = new User();
        user.setUsername(map.get("username"));
        user.setPassword(encryption.encryptionPsw(map.get("password")));//对明文加密
        user.setNickname(map.get("nickname"));
        user.setTelephone(map.get("telephone"));
        userFeign.save(user);
        result.setCode(1);
        result.setMsg("保存成功");
        return result;
    }

    @PutMapping("/update")
    public void update(@RequestBody User user){
        userFeign.update(user);
    }

    @RequestMapping("/deleteById")
    public void deleteById(long id){

        userFeign.deleteById(id);
    }

}
