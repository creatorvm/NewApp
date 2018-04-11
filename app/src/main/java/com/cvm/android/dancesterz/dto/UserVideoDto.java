package com.cvm.android.dancesterz.dto;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 18-01-2018.
 */

public class UserVideoDto implements Serializable {
    private final static long serialVersionUID = 1L;

    protected Long videoId;

    protected Long userId;

    Boolean fasadResponseBoolean;
    String actions;

    public UserVideoDto() {

    }

    public Boolean getFasadResponseBoolean() {
        return fasadResponseBoolean;
    }

    public void setFasadResponseBoolean(Boolean fasadResponseBoolean) {
        this.fasadResponseBoolean = fasadResponseBoolean;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public UserVideoDto(Long videoId, Long userId, Boolean fasadResponseBoolean, String actions) {
        this.videoId = videoId;
        this.userId = userId;
        this.fasadResponseBoolean = fasadResponseBoolean;
        this.actions = actions;
    }

    public UserVideoDto(Long videoId, Long userId) {
        this.videoId = videoId;
        this.userId = userId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
