package com.agentproject.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "sys_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "菜单名称不能为空")
    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String path;

    @Column(length = 200)
    private String component;

    @Column(length = 100)
    private String icon;

    @Column(nullable = false)
    private Integer sortOrder = 0;

    @Column(nullable = false, length = 50)
    private String permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Column(name = "is_menu", nullable = false)
    private boolean isMenu = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }
    public Menu getParent() { return parent; }
    public void setParent(Menu parent) { this.parent = parent; }
    public boolean isMenu() { return isMenu; }
    public void setMenu(boolean menu) { isMenu = menu; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
