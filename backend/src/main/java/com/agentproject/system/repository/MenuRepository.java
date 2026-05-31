package com.agentproject.system.repository;

import com.agentproject.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByParentIsNullOrderBySortOrder();
    List<Menu> findByParentIdOrderBySortOrder(Long parentId);
}
