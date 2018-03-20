package com.cvm.android.dancesterz.dto;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */

public class UserCreateRequest {
    public User user;
    private final static long serialVersionUID = 1L;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
