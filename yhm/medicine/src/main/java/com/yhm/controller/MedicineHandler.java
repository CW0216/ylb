package com.yhm.controller;


import com.yhm.entity.Medicine;
import com.yhm.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineHandler {

    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping("/findAll/{index}/{limit}")
    public List<Medicine> findAll(@PathVariable("index")int index, @PathVariable("limit") int limit){
        return medicineRepository.findAll(index,limit);
    }

    @GetMapping("findById/{id}")
    public Medicine findById(@PathVariable("id") long id){
        return medicineRepository.findById(id);
    }

    @GetMapping("/findByType/{index}/{limit}/{m_type}")
    public List<Medicine> findByType(@PathVariable("index")int index, @PathVariable("limit") int limit,@PathVariable("m_type") int m_type){
        return medicineRepository.findByType(index,limit,m_type);
    }

    @GetMapping("/count")
    public int count(){
        return medicineRepository.count();
    }

    @GetMapping("/countType/{m_type}")
    public int countType(@PathVariable("m_type") int m_type){
        return medicineRepository.countType(m_type);
    }

    @PostMapping("/save")
    public void save(@RequestBody Medicine medicine){
        medicineRepository.save(medicine);
    }

    @PutMapping("/update")
    public void update(@RequestBody Medicine medicine){
        medicineRepository.update(medicine);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") long id){
        medicineRepository.deleteById(id);
    }

}
