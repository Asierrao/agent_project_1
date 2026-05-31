package com.agentproject.system.service;

import com.agentproject.system.entity.Dept;
import com.agentproject.system.repository.DeptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    private final DeptRepository deptRepository;

    public DeptService(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    public List<Dept> getTree() {
        return deptRepository.findByParentIsNullOrderBySortOrder();
    }

    public Dept findById(Long id) {
        return deptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("部门不存在"));
    }

    public Dept create(Dept dept) {
        return deptRepository.save(dept);
    }

    public Dept update(Long id, Dept dept) {
        Dept existing = findById(id);
        existing.setName(dept.getName());
        existing.setDescription(dept.getDescription());
        existing.setSortOrder(dept.getSortOrder());
        return deptRepository.save(existing);
    }

    public void delete(Long id) {
        deptRepository.deleteById(id);
    }
}
