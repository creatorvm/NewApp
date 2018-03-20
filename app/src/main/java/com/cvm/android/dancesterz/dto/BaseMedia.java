package com.cvm.android.dancesterz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseMedia implements Serializable {
    @JsonProperty
    private final static long serialVersionUID = 1L;
    @JsonProperty
    protected String sourcePath;
    @JsonProperty
    protected String title;
    @JsonProperty
    protected String desc;
    @JsonProperty
    protected String format;
    @JsonProperty
    protected String duration;
    @JsonProperty
    protected Long id;



    public BaseMedia() {

    }

    public BaseMedia(String sourcePath, String title, String desc, String format, String duration, Long id) {
        this.sourcePath = sourcePath;
        this.title = title;
        this.desc = desc;
        this.format = format;
        this.duration = duration;
        this.id = id;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
