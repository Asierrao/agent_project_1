package com.agentproject.crm.controller;

import com.agentproject.common.response.Result;
import com.agentproject.crm.entity.SalesOrder;
import com.agentproject.crm.service.SalesOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crm/sales-orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @GetMapping
    public Result<List<SalesOrder>> findAll() {
        return Result.success(salesOrderService.findAll());
    }

    @GetMapping("/{id}")
    public Result<SalesOrder> findById(@PathVariable Long id) {
        return Result.success(salesOrderService.findById(id));
    }

    @PostMapping
    public Result<SalesOrder> create(@Valid @RequestBody SalesOrder order) {
        return Result.success(salesOrderService.create(order));
    }

    @PutMapping("/{id}/status")
    public Result<SalesOrder> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        SalesOrder.OrderStatus status = SalesOrder.OrderStatus.valueOf(body.get("status"));
        return Result.success(salesOrderService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        salesOrderService.delete(id);
        return Result.success();
    }
}
