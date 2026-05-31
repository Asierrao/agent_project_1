package com.agentproject.system.repository;

import com.agentproject.system.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {
    List<Dept> findByParentIsNullOrderBySortOrder();
    List<Dept> findByParentIdOrderBySortOrder(Long parentId);
}
