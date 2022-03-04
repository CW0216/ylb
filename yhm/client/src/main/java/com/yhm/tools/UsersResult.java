package com.yhm.tools;

import com.yhm.entity.User;
import lombok.Data;

import java.util.List;
@Data
public class UsersResult extends Result{
    public int count;
    public List<User> data;
}
