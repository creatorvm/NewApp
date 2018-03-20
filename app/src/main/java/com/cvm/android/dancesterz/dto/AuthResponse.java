package com.cvm.android.dancesterz.dto;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */

public class AuthResponse {
    private final static long serialVersionUID = 1L;
    protected UserAuth user;
    protected String token;
    protected String refreshToken;
//    protected String requestSource;
    public String getRefreshToken() {
        return refreshToken;
    }

//    public AuthResponse(UserAuth user, String token, String refreshToken, String requestSource) {
//        this.user = user;
//        this.token = token;
//        this.refreshToken = refreshToken;
//        this.requestSource = requestSource;
//    }

    public AuthResponse(UserAuth user, String token, String refreshToken) {
        this.user = user;
        this.token = token;
        this.refreshToken = refreshToken;
    }

//    public String getRequestSource() {
//        return requestSource;
//    }

    public AuthResponse() {
    }
//    public void setRequestSource(String requestSource) {
//        this.requestSource = requestSource;
//    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserAuth getUser() {
        return user;
    }

    public void setUser(UserAuth user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
