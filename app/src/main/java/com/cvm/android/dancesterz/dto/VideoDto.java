package com.cvm.android.dancesterz.dto;

/**
 * Created by Devalopment-1 on 09-01-2018.
 */

public class VideoDto extends BaseMedia {
    private final static long serialVersionUID = 1L;
    protected String thumbnail;
    protected Long audioId;
    protected String thumbnailSeek;

    public VideoDto() {
    }

    public VideoDto(String sourcePath, String title, String desc, String format, String duration, Long id, String thumbnail, Long audioId) {

        super(sourcePath, title, desc, format, duration, id);
        this.thumbnail = thumbnail;
        this.audioId = audioId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailSeek() {
        return thumbnailSeek;
    }

    public void setThumbnailSeek(String thumbnailSeek) {
        this.thumbnailSeek = thumbnailSeek;
    }

    public VideoDto(String thumbnail, Long audioId) {
        this.thumbnail = thumbnail;
        this.audioId = audioId;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getAudioId() {
        return audioId;
    }

    public void setAudioId(Long audioId) {
        this.audioId = audioId;
    }
}

