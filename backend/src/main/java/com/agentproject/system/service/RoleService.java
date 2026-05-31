package com.agentproject.system.service;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.system.entity.Role;
import com.agentproject.system.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("角色不存在"));
    }

    public Role create(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new BusinessException("角色名称已存在");
        }
        return roleRepository.save(role);
    }

    public Role update(Long id, Role role) {
        Role existing = findById(id);
        existing.setName(role.getName());
        existing.setDescription(role.getDescription());
        existing.setMenus(role.getMenus());
        return roleRepository.save(existing);
    }

    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new BusinessException("角色不存在");
        }
        roleRepository.deleteById(id);
    }
}
