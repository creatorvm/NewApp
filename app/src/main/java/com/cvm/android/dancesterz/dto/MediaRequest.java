package com.cvm.android.dancesterz.dto;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */

public class MediaRequest extends BaseRequest {
    private final static long serialVersionUID = 1L;
    protected String mediaType;
    protected String url;
    protected String title;
    protected String name;
    protected String desc;
    protected Long userId;


    public MediaRequest(String mediaType, String url, String title, String name, String desc) {
        this.mediaType = mediaType;
        this.url = url;
        this.title = title;
        this.name = name;
        this.desc = desc;
    }

    public MediaRequest(String mediaType, String title, Long userId) {
        this.mediaType = mediaType;
        this.title = title;
        this.userId=userId;
    }
    public MediaRequest(String mediaType, String title) {
        this.mediaType = mediaType;
        this.title = title;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
