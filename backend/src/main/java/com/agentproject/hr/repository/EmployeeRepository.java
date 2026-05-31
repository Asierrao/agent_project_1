package com.agentproject.hr.repository;

import com.agentproject.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeNo(String employeeNo);
    List<Employee> findByDeptId(Long deptId);
    List<Employee> findByStatus(Employee.EmployeeStatus status);
}
