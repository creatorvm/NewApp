package com.cvm.android.dancesterz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaResponse extends BaseResponse implements Serializable {

    @JsonProperty
    private final static long serialVersionUID = 1L;
    @JsonProperty
    protected String mediaType;
    @JsonProperty
    protected List<BaseMedia> media;
    @JsonProperty
    protected List<AudioDto> audioList;
    @JsonProperty
    protected List<VideoDto> videoList;
//    @JsonProperty
//    String requestSource;
//
//    @Override
//    public String getRequestSource() {
//        return requestSource;
//    }
    public List<VideoDto> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoDto> videoList) {
        this.videoList = videoList;
    }
//
//    @Override
//    public void setRequestSource(String requestSource) {
//        this.requestSource = requestSource;
//    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public List<BaseMedia> getMedia() {
        return media;
    }

    public void setMedia(List<BaseMedia> media) {
        this.media = media;
    }

    public List<AudioDto> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<AudioDto> audioList) {
        this.audioList = audioList;
    }
}
