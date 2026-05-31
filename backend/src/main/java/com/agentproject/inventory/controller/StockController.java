package com.agentproject.inventory.controller;

import com.agentproject.common.response.Result;
import com.agentproject.inventory.entity.Stock;
import com.agentproject.inventory.entity.StockMovement;
import com.agentproject.inventory.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory/stocks")
public class StockController {

    private final ProductService productService;

    public StockController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{productId}")
    public Result<List<Stock>> getByProduct(@PathVariable Long productId) {
        return Result.success(productService.getStockByProduct(productId));
    }

    @GetMapping("/product/{productId}/warehouse/{warehouseId}")
    public Result<Stock> getStock(@PathVariable Long productId, @PathVariable Long warehouseId) {
        return Result.success(productService.getStock(productId, warehouseId));
    }

    @PostMapping("/movement")
    public Result<StockMovement> moveStock(@RequestBody Map<String, Object> body,
                                            Authentication auth) {
        Long productId = Long.valueOf(body.get("productId").toString());
        Long warehouseId = Long.valueOf(body.get("warehouseId").toString());
        StockMovement.MovementType type = StockMovement.MovementType.valueOf(body.get("type").toString());
        int quantity = Integer.parseInt(body.get("quantity").toString());
        String remark = (String) body.getOrDefault("remark", "");
        String username = auth != null ? auth.getName() : "system";

        return Result.success(productService.moveStock(productId, warehouseId, type, quantity, remark, username));
    }
}
