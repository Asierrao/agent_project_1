package com.agentproject.hr.service;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.hr.entity.Employee;
import com.agentproject.hr.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("员工不存在"));
    }

    public Employee create(Employee employee) {
        if (employeeRepository.existsByEmployeeNo(employee.getEmployeeNo())) {
            throw new BusinessException("员工编号已存在");
        }
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        Employee existing = findById(id);
        existing.setName(employee.getName());
        existing.setGender(employee.getGender());
        existing.setPhone(employee.getPhone());
        existing.setEmail(employee.getEmail());
        existing.setAddress(employee.getAddress());
        existing.setIdCard(employee.getIdCard());
        existing.setBirthDate(employee.getBirthDate());
        existing.setPosition(employee.getPosition());
        existing.setHireDate(employee.getHireDate());
        existing.setStatus(employee.getStatus());
        existing.setDeptId(employee.getDeptId());
        return employeeRepository.save(existing);
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new BusinessException("员工不存在");
        }
        employeeRepository.deleteById(id);
    }
}
