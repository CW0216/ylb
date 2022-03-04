package com.yhm.controller;

import com.yhm.entity.Medicine;

import com.yhm.tools.MedicineResult;
import com.yhm.tools.Result;
import com.yhm.feign.MedicineFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/medicine")
@CrossOrigin
public class MedicineHandler {
    @Autowired
    private MedicineFeign medicineFeign;

    @RequestMapping("/findAll")
    public MedicineResult findAll(@RequestParam("page") int page, @RequestParam("limit")int limit){
        int index=(page-1)*limit;
        MedicineResult medicineResult = new MedicineResult();
        medicineResult.setCode(0);
        medicineResult.setMsg("成功");
        medicineResult.setCount(medicineFeign.count());
        medicineResult.setData(medicineFeign.findAll(index,limit));
        return medicineResult;
    }

    @RequestMapping("findById/{id}")
    public Medicine findById(@PathVariable("id") long id){
        return medicineFeign.findById(id);
    }

    @RequestMapping("findByType")
    public MedicineResult findByType(@RequestParam("page") int page, @RequestParam("limit")int limit, int m_type){
        int index=(page-1)*limit;
        MedicineResult medicineResult = new MedicineResult();
        medicineResult.setCode(0);
        medicineResult.setMsg("成功");
        medicineResult.setCount(medicineFeign.countType(m_type));
        medicineResult.setData(medicineFeign.findByType(index,limit,m_type));
        return medicineResult;
    }


    @GetMapping("/count")
    public int count(){
        return medicineFeign.count();
    }

    @GetMapping("/countType")
    public int countType(int m_type){
        return medicineFeign.countType(m_type);
    }


    @RequestMapping("/save")
    public Result save(@RequestBody Map<String, String> map){
        System.out.println(map);
        Medicine medicine = new Medicine();
        Result result=new Result();
        medicine.setM_name(map.get("m_name"));
        medicine.setImg(map.get("file"));
        medicine.setPrice(Float.valueOf(map.get("price")).floatValue());
        medicine.setCompany(map.get("company"));
        medicine.setM_type(Integer.parseInt(map.get("m_type")));
        medicine.setStock(Integer.parseInt(map.get("stock")));
        medicine.setDetail(map.get("detail"));
        medicine.setSales(0);
        medicineFeign.save(medicine);
        result.setCode(1);
        result.setMsg("新增药品成功");
        return result;
    }

    @RequestMapping("/saveImg")
    public Result saveImg(MultipartFile file){
        Result result=new Result();
        String newFile=UUID.randomUUID().toString()+file.getOriginalFilename();
        try {
            file.transferTo(new File("D:/yhm1.0/yihe-new-village-project-team/img/",newFile));
            result.setCode(1);
            result.setMsg("http://localhost:8090/"+newFile);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.setCode(0);
        result.setMsg("上传失败");
        return result;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Map<String, String> map){
        Medicine medicine = new Medicine();
        Result result=new Result();
        medicine.setM_name(map.get("m_name"));
        medicine.setPrice(Float.parseFloat(map.get("price")));
        medicine.setCompany(map.get("company"));
        medicine.setM_type(Integer.parseInt(map.get("m_type")));
        medicine.setSales(Integer.parseInt(map.get("sales")));
        medicine.setId(Long.parseLong(map.get("id")));
        medicine.setStock(Integer.parseInt(map.get("stock")));
        medicineFeign.update(medicine);
        result.setCode(1);
        result.setMsg("修改药品成功");
        return result;
    }



    @RequestMapping("/deleteById")
    public Result deleteById(@RequestBody Map<String, String> map){
        Result result = new Result();
        long id=Long.parseLong(map.get("id"));
        medicineFeign.deleteById(id);
        result.setCode(1);
        result.setMsg("删除药品成功");
        return result;
    }
}
