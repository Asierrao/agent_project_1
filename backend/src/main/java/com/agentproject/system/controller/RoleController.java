package com.agentproject.system.controller;

import com.agentproject.common.response.Result;
import com.agentproject.system.entity.Role;
import com.agentproject.system.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Result<List<Role>> findAll() {
        return Result.success(roleService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Role> findById(@PathVariable Long id) {
        return Result.success(roleService.findById(id));
    }

    @PostMapping
    public Result<Role> create(@Valid @RequestBody Role role) {
        return Result.success(roleService.create(role));
    }

    @PutMapping("/{id}")
    public Result<Role> update(@PathVariable Long id, @Valid @RequestBody Role role) {
        return Result.success(roleService.update(id, role));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return Result.success();
    }
}
