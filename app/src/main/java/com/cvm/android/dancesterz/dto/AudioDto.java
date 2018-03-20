package com.cvm.android.dancesterz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Development-2 on 13-12-2017.
 */
public class AudioDto extends BaseMedia implements Serializable {

    private final static long serialVersionUID = 1L;

    private String track;

    private String artist;

    public AudioDto() {
    }

    public AudioDto(String track, String artist) {
        this.track = track;
        this.artist = artist;
    }

    public AudioDto(String track) {
        this.track = track;
    }

    public String gettrack() {
        return track;
    }

    public void settrack(String track) {
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
