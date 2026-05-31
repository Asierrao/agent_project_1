package com.agentproject.inventory.controller;

import com.agentproject.common.response.Result;
import com.agentproject.inventory.entity.Product;
import com.agentproject.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Result<List<Product>> findAll() {
        return Result.success(productService.findAllProducts());
    }

    @GetMapping("/{id}")
    public Result<Product> findById(@PathVariable Long id) {
        return Result.success(productService.findProductById(id));
    }

    @PostMapping
    public Result<Product> create(@Valid @RequestBody Product product) {
        return Result.success(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    public Result<Product> update(@PathVariable Long id, @Valid @RequestBody Product product) {
        return Result.success(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Result.success();
    }
}
