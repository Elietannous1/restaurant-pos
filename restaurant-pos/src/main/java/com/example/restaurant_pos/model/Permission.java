package com.example.restaurant_pos.model;

public enum Permission {

    ADMIN_CREATE("admin:create"),
    MANAGER_CREATE("management:create");

    private final String permission;
    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() { return permission; }
}
