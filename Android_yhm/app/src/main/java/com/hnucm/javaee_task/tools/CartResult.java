package com.hnucm.javaee_task.tools;


import com.hnucm.javaee_task.entity.Cart;
import com.hnucm.javaee_task.entity.Medicine;
import com.hnucm.javaee_task.entity.User;

import java.util.Date;
import java.util.List;


public class CartResult extends Result{
    public int count;
    public List<Cart> data;
}
