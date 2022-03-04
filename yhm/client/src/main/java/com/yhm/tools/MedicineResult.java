package com.yhm.tools;

import com.yhm.entity.Medicine;
import lombok.Data;

import java.util.List;
@Data
public class MedicineResult extends Result {
    public int count;
    public List<Medicine> data;
}