package com.agentproject.crm.repository;

import com.agentproject.crm.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    boolean existsByOrderNo(String orderNo);
}
