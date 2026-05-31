package com.agentproject.system.controller;

import com.agentproject.common.response.Result;
import com.agentproject.system.entity.Menu;
import com.agentproject.system.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/tree")
    public Result<List<Menu>> getTree() {
        return Result.success(menuService.getTree());
    }

    @GetMapping
    public Result<List<Menu>> findAll() {
        return Result.success(menuService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Menu> findById(@PathVariable Long id) {
        return Result.success(menuService.findById(id));
    }

    @PostMapping
    public Result<Menu> create(@Valid @RequestBody Menu menu) {
        return Result.success(menuService.create(menu));
    }

    @PutMapping("/{id}")
    public Result<Menu> update(@PathVariable Long id, @Valid @RequestBody Menu menu) {
        return Result.success(menuService.update(id, menu));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return Result.success();
    }
}
