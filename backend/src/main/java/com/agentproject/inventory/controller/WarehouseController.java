package com.agentproject.inventory.controller;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.common.response.Result;
import com.agentproject.inventory.entity.Warehouse;
import com.agentproject.inventory.repository.WarehouseRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/warehouses")
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @GetMapping
    public Result<List<Warehouse>> findAll() {
        return Result.success(warehouseRepository.findAll());
    }

    @GetMapping("/{id}")
    public Result<Warehouse> findById(@PathVariable Long id) {
        return Result.success(warehouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("仓库不存在")));
    }

    @PostMapping
    public Result<Warehouse> create(@Valid @RequestBody Warehouse warehouse) {
        if (warehouseRepository.existsByName(warehouse.getName())) {
            throw new BusinessException("仓库名称已存在");
        }
        return Result.success(warehouseRepository.save(warehouse));
    }

    @PutMapping("/{id}")
    public Result<Warehouse> update(@PathVariable Long id, @Valid @RequestBody Warehouse warehouse) {
        Warehouse existing = warehouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("仓库不存在"));
        existing.setName(warehouse.getName());
        existing.setAddress(warehouse.getAddress());
        existing.setDescription(warehouse.getDescription());
        return Result.success(warehouseRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        warehouseRepository.deleteById(id);
        return Result.success();
    }
}
