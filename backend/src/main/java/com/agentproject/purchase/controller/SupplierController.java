package com.agentproject.purchase.controller;

import com.agentproject.common.response.Result;
import com.agentproject.purchase.entity.Supplier;
import com.agentproject.purchase.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public Result<List<Supplier>> findAll() {
        return Result.success(supplierService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Supplier> findById(@PathVariable Long id) {
        return Result.success(supplierService.findById(id));
    }

    @PostMapping
    public Result<Supplier> create(@Valid @RequestBody Supplier supplier) {
        return Result.success(supplierService.create(supplier));
    }

    @PutMapping("/{id}")
    public Result<Supplier> update(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
        return Result.success(supplierService.update(id, supplier));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return Result.success();
    }
}
