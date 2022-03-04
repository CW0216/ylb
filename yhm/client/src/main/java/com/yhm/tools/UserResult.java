package com.yhm.tools;

import com.yhm.entity.User;
import lombok.Data;

@Data
public class UserResult extends Result{
    public User user;
}
