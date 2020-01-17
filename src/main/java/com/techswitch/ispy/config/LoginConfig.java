package com.techswitch.ispy.config;

public class LoginConfig {
    private String adminUsername;
    private String adminPassword;

    public LoginConfig(String adminUsername, String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}

