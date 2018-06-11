package com.cvm.android.dancesterz.dto;

import java.io.Serializable;
import java.util.List;

public class ChallengeVisibility implements Serializable {
    private final static long serialVersionUID = 1L;
    protected List<UserSummary> users;
    protected List<GroupDto> groups;
    protected String visibilityCode;
    protected String visibilityDesc;

    public List<UserSummary> getUsers() {
        return users;
    }

    public void setUsers(List<UserSummary> users) {
        this.users = users;
    }

    public List<GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDto> groups) {
        this.groups = groups;
    }

    public String getVisibilityCode() {
        return visibilityCode;
    }

    public void setVisibilityCode(String visibilityCode) {
        this.visibilityCode = visibilityCode;
    }

    public String getVisibilityDesc() {
        return visibilityDesc;
    }

    public void setVisibilityDesc(String visibilityDesc) {
        this.visibilityDesc = visibilityDesc;
    }
}
