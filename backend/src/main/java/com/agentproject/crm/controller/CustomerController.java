package com.agentproject.crm.controller;

import com.agentproject.common.response.Result;
import com.agentproject.crm.entity.Customer;
import com.agentproject.crm.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crm/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Result<List<Customer>> findAll() {
        return Result.success(customerService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Customer> findById(@PathVariable Long id) {
        return Result.success(customerService.findById(id));
    }

    @PostMapping
    public Result<Customer> create(@Valid @RequestBody Customer customer) {
        return Result.success(customerService.create(customer));
    }

    @PutMapping("/{id}")
    public Result<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        return Result.success(customerService.update(id, customer));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return Result.success();
    }
}
