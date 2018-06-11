package com.cvm.android.dancesterz.dto;

import java.io.Serializable;
import java.util.List;

public class GroupDto implements Serializable {
    private final static long serialVersionUID = 1L;
    protected List<GroupMembersDto> members;
    protected String groupName;
    protected String groupDesc;
    protected Long groupId;
    protected String visibility;
    protected String status;
    protected String groupThumbnail;

    public List<GroupMembersDto> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMembersDto> members) {
        this.members = members;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupThumbnail() {
        return groupThumbnail;
    }

    public void setGroupThumbnail(String groupThumbnail) {
        this.groupThumbnail = groupThumbnail;
    }
}
