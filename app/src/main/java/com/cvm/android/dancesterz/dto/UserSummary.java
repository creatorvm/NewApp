package com.cvm.android.dancesterz.dto;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 18-01-2018.
 */

public class UserSummary implements Serializable {

    private final static long serialVersionUID = 1L;
    protected long id;
    protected String userName;
    protected String profileImage;
    protected String firstName;
    protected String lastName;
    protected String nickName;


    public UserSummary(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public UserSummary(long id, String userName, String profileImage, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.profileImage = profileImage;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserSummary() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
