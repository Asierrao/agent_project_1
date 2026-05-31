package com.agentproject.purchase.controller;

import com.agentproject.common.response.Result;
import com.agentproject.purchase.entity.PurchaseOrder;
import com.agentproject.purchase.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase/orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public Result<List<PurchaseOrder>> findAll() {
        return Result.success(purchaseOrderService.findAll());
    }

    @GetMapping("/{id}")
    public Result<PurchaseOrder> findById(@PathVariable Long id) {
        return Result.success(purchaseOrderService.findById(id));
    }

    @PostMapping
    public Result<PurchaseOrder> create(@Valid @RequestBody PurchaseOrder order) {
        return Result.success(purchaseOrderService.create(order));
    }

    @PutMapping("/{id}/status")
    public Result<PurchaseOrder> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        PurchaseOrder.OrderStatus status = PurchaseOrder.OrderStatus.valueOf(body.get("status"));
        return Result.success(purchaseOrderService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        purchaseOrderService.delete(id);
        return Result.success();
    }
}
