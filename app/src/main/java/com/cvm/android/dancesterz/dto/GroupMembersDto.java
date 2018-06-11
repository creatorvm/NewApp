package com.cvm.android.dancesterz.dto;

import java.io.Serializable;

public class GroupMembersDto implements Serializable {
    private static final long serialVersionUID = 1L;
    protected UserSummary user;
    protected String permission;

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
