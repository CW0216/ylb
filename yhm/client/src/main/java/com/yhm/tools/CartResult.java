package com.yhm.tools;

import com.yhm.entity.Cart;
import lombok.Data;

import java.util.List;

@Data
public class CartResult extends Result{
    public int count;
    public List<Cart> data;
}
