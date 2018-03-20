package com.cvm.android.dancesterz.dto;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */

public class AuthRequest {
    private final static long serialVersionUID = 1L;
    protected String username;
    protected String password;
    protected String clientSystem;
    protected String authSystem;
    protected String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientSystem() {
        return clientSystem;
    }

    public void setClientSystem(String clientSystem) {
        this.clientSystem = clientSystem;
    }

    public AuthRequest(String username, String password, String clientSystem, String authSystem) {
        this.username = username;
        this.password = password;
        this.clientSystem = clientSystem;
        this.authSystem = authSystem;
    }

    public AuthRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthSystem() {
        return authSystem;

    }

    public void setAuthSystem(String authSystem) {
        this.authSystem = authSystem;
    }
}

