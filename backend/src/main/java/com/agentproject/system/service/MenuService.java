package com.agentproject.system.service;

import com.agentproject.system.entity.Menu;
import com.agentproject.system.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getTree() {
        return menuRepository.findByParentIsNullOrderBySortOrder();
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("菜单不存在"));
    }

    public Menu create(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu update(Long id, Menu menu) {
        Menu existing = findById(id);
        existing.setName(menu.getName());
        existing.setPath(menu.getPath());
        existing.setComponent(menu.getComponent());
        existing.setIcon(menu.getIcon());
        existing.setSortOrder(menu.getSortOrder());
        existing.setPermission(menu.getPermission());
        existing.setMenu(menu.isMenu());
        return menuRepository.save(existing);
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}
