package com.yhm.repository;

import com.yhm.entity.Medicine;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicineRepository {
    public List<Medicine> findAll(int index, int limit);
    public Medicine findById(long id);
    public List<Medicine> findByType(int index, int limit,int m_type);
    public int count();
    public int countType(int m_type);
    public void save(Medicine medicine);
    public void update(Medicine medicine);
    public void deleteById(long id);
}
