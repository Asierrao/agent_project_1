package com.agentproject.system.controller;

import com.agentproject.common.response.Result;
import com.agentproject.system.entity.Dept;
import com.agentproject.system.service.DeptService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/depts")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/tree")
    public Result<List<Dept>> getTree() {
        return Result.success(deptService.getTree());
    }

    @GetMapping("/{id}")
    public Result<Dept> findById(@PathVariable Long id) {
        return Result.success(deptService.findById(id));
    }

    @PostMapping
    public Result<Dept> create(@Valid @RequestBody Dept dept) {
        return Result.success(deptService.create(dept));
    }

    @PutMapping("/{id}")
    public Result<Dept> update(@PathVariable Long id, @Valid @RequestBody Dept dept) {
        return Result.success(deptService.update(id, dept));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        deptService.delete(id);
        return Result.success();
    }
}
