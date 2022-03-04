package com.yhm.entity;

import lombok.Data;

@Data
public class Medicine {
    private long id;
    private String m_name;
    private float price;
    private long sales;
    private String company;
    private int m_type;
    private String detail;
    private String img;
}
