package com.yhm.feign;

import com.yhm.entity.Medicine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "medicine")
public interface MedicineFeign {

    @GetMapping("/medicine/findAll/{index}/{limit}")
    public List<Medicine> findAll(@PathVariable("index") int index, @PathVariable("limit") int limit);

    @GetMapping("/medicine/findById/{id}")
    public Medicine findById(@PathVariable("id") long id);

    @GetMapping("/medicine/findByType/{index}/{limit}/{m_type}")
    public List<Medicine> findByType(@PathVariable("index") int index, @PathVariable("limit") int limit,@PathVariable("m_type") int m_type);

    @GetMapping("/medicine/count")
    public int count();

    @GetMapping("/medicine/countType/{m_type}")
    public int countType(@PathVariable("m_type") int m_type);

    @PostMapping("/medicine/save")
    public void save(@RequestBody Medicine medicine);

    @PutMapping("/medicine/update")
    public void update(@RequestBody Medicine medicine);

    @DeleteMapping("/medicine/deleteById/{id}")
    public void deleteById(@PathVariable("id") long id);
}
