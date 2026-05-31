package com.agentproject.inventory.controller;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.common.response.Result;
import com.agentproject.inventory.entity.Category;
import com.agentproject.inventory.repository.CategoryRepository;
import com.agentproject.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/categories")
public class CategoryController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public CategoryController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/tree")
    public Result<List<Category>> getTree() {
        return Result.success(productService.getCategoryTree());
    }

    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable Long id) {
        return Result.success(categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("分类不存在")));
    }

    @PostMapping
    public Result<Category> create(@Valid @RequestBody Category category) {
        return Result.success(productService.createCategory(category));
    }

    @PutMapping("/{id}")
    public Result<Category> update(@PathVariable Long id, @Valid @RequestBody Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("分类不存在"));
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        existing.setSortOrder(category.getSortOrder());
        existing.setParent(category.getParent());
        return Result.success(categoryRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return Result.success();
    }
}
