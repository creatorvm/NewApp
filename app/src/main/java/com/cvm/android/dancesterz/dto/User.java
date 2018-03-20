package com.cvm.android.dancesterz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Devalopment-1 on 20-12-2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends UserSummary implements Serializable {

    private final static long serialVersionUID = 1L;
    @JsonProperty
    protected String email;
    @JsonProperty
    protected String phoneNumber;
    @JsonProperty
    protected UserAuth userAuth;
    @JsonProperty
    protected String dob;
    @JsonProperty
    protected Long totalVotes;
    @JsonProperty
    protected String thumbnail;
    @JsonProperty
    protected List<String> userDanceStyle;
    @JsonProperty
    protected List<String> userDanceStyleWholeList;
    @JsonProperty
    protected String city;
    @JsonProperty
    protected String state;
    @JsonProperty
    protected String country;
    @JsonProperty
    protected String gender;
    @JsonProperty
    protected String status;
    @JsonProperty
    protected String type;
    @JsonProperty
    protected Date createdDate;
    @JsonProperty
    protected Date updatedDate;

    public User() {
    }

    public User(String nickName, String userName, String email, String phoneNumber, UserAuth userAuth, String dob, List<String> userDanceStyle, List<String> userDanceStyleWholeList, String firstName, String lastName, String city, String country, String state, String gender, String status, String type) {
        super.nickName = nickName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userAuth = userAuth;
        this.dob = dob;
        this.userDanceStyle = userDanceStyle;
        this.userDanceStyleWholeList = userDanceStyleWholeList;
//        this.professionalLevel = professionalLevel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.state = state;
        this.gender = gender;
        this.status = status;
        this.type = type;
    }

    public Long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


//    public User(String nickName, String email, String phoneNumber, UserAuth userAuth, String dob, List<String> userDanceStyle, List<String> userDanceStyleWholeList, String firstName, String lastName, String city, String country, String state, String gender, String status, String type) {
//
//
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.userAuth = userAuth;
//        this.dob = dob;
//        this.userDanceStyle = userDanceStyle;
//        this.userDanceStyleWholeList = userDanceStyleWholeList;
////        this.professionalLevel = professionalLevel;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.city = city;
//        this.country = country;
//        this.state = state;
//        this.gender = gender;
//        this.status = status;
//        this.type = type;
//    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<String> getUserDanceStyle() {
        return userDanceStyle;
    }

    public void setUserDanceStyle(List<String> userDanceStyle) {
        this.userDanceStyle = userDanceStyle;
    }

    public List<String> getUserDanceStyleWholeList() {
        return userDanceStyleWholeList;
    }

    public void setUserDanceStyleWholeList(List<String> userDanceStyleWholeList) {
        this.userDanceStyleWholeList = userDanceStyleWholeList;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}

