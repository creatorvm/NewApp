package com.cvm.android.dancesterz.dto;

/**
 * Created by Devalopment-1 on 20-12-2017.
 */

import java.util.List;

public class UserResponse {
    String requestSource;
    private final static long serialVersionUID = 1L;
    protected List<User> user;

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public List<User> getUser()
    {

        return this.user;
    }

    public void setUser(List<User> user)
    {
        this.user = user;
    }
}
