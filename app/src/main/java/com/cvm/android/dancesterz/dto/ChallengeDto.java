package com.cvm.android.dancesterz.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Devalopment-1 on 12-01-2018.
 */

public class ChallengeDto implements Serializable {
    private final static long serialVersionUID = 1L;

    protected UserSummary owner;
    protected String desc;
    protected Long challengeId;
    protected String name;
    protected VideoDto video;
    protected String expireDate;
    protected Long userId;
    protected String sourcePath;
    protected String title;
    protected List<AcceptChallengeDto> responses;
    protected AudioDto audio;
    Long totalVotes;
    protected Long challengeStatus;

    public UserSummary getOwner() {
        return owner;
    }

    public ChallengeDto() {
    }

    public ChallengeDto(UserSummary owner, String desc, Long challengeId, String name, VideoDto video, String expireDate, Long userId, String sourcePath, String title, List<AcceptChallengeDto> responses, AudioDto audio, Long totalVotes) {
        this.owner = owner;
        this.desc = desc;
        this.challengeId = challengeId;
        this.name = name;
        this.video = video;
        this.expireDate = expireDate;
        this.userId = userId;
        this.sourcePath = sourcePath;
        this.title = title;
        this.responses = responses;
        this.audio = audio;
        this.totalVotes = totalVotes;
    }

    public ChallengeDto(String desc, Long challengeId, String name, String title) {
        this.desc = desc;
        this.challengeId = challengeId;
        this.name = name;
        this.title = title;
    }

    public Long getChallengeStatus() {
        return challengeStatus;
    }

    public void setChallengeStatus(Long challengeStatus) {
        this.challengeStatus = challengeStatus;
    }

    public ChallengeDto(UserSummary owner, String desc, Long challengeId, String name, VideoDto video, String expireDate, Long userId, String sourcePath, String title, List<AcceptChallengeDto> responses) {
        this.owner = owner;
        this.desc = desc;
        this.challengeId = challengeId;
        this.name = name;
        this.video = video;
        this.expireDate = expireDate;
        this.userId = userId;
        this.sourcePath = sourcePath;
        this.title = title;
        this.responses = responses;
    }

    public Long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public AudioDto getAudio() {
        return audio;
    }

    public void setAudio(AudioDto audio) {
        this.audio = audio;
    }

    public void setOwner(UserSummary owner) {
        this.owner = owner;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VideoDto getVideo() {
        return video;
    }

    public void setVideo(VideoDto video) {
        this.video = video;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<AcceptChallengeDto> getResponses() {
        return responses;
    }

    public void setResponses(List<AcceptChallengeDto> responses) {
        this.responses = responses;
    }

}
