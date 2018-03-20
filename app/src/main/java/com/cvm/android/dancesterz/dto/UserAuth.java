package com.cvm.android.dancesterz.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devalopment-1 on 20-12-2017.
 */

public class UserAuth {
    private final static long serialVersionUID = 1L;

    protected String token;

    protected String refreshToken;

    protected String clientSystem;

    protected String authSystem;

    protected List<Role> roles;

    protected String username;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    protected String password;

    protected Long userId;

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientSystem() {
        return clientSystem;
    }

    public void setClientSystem(String clientSystem) {
        this.clientSystem = clientSystem;
    }

    public String getAuthSystem() {
        return authSystem;
    }

    public void setAuthSystem(String authSystem) {
        this.authSystem = authSystem;
    }

    public UserAuth() {
    }

    public UserAuth(String token, String refreshToken, List<Role> roles, String username, String password, Long userId) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public UserAuth(String token, String clientSystem, String authSystem, List<Role> roles, String username, String password, Long userId) {
        this.token = token;
        this.clientSystem = clientSystem;
        this.authSystem = authSystem;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.userId=userId;

    }

    public UserAuth(String token, String clientSystem, String authSystem, List<Role> roles, String username, String password) {
        this.token = token;
        this.clientSystem = clientSystem;
        this.authSystem = authSystem;
        this.roles = roles;
        this.username = username;
        this.password = password;
    }

    public UserAuth(String clientSystem, String authSystem, String username, String password) {
        this.clientSystem = clientSystem;
        this.authSystem = authSystem;
        this.username = username;
        this.password = password;
    }

    public UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<Role> getRoles() {
        if (roles == null) {
            roles = new ArrayList<Role>();
        }

        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

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
}
