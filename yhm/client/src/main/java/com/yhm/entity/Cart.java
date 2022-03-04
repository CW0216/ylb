package com.yhm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Cart {
    private long id;
    private User user;
    private Medicine medicine;
    private Date date;
    private int state;
}
