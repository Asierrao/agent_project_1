package com.agentproject.hr.controller;

import com.agentproject.common.response.Result;
import com.agentproject.hr.entity.Employee;
import com.agentproject.hr.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Result<List<Employee>> findAll() {
        return Result.success(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Employee> findById(@PathVariable Long id) {
        return Result.success(employeeService.findById(id));
    }

    @PostMapping
    public Result<Employee> create(@Valid @RequestBody Employee employee) {
        return Result.success(employeeService.create(employee));
    }

    @PutMapping("/{id}")
    public Result<Employee> update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return Result.success(employeeService.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return Result.success();
    }
}
