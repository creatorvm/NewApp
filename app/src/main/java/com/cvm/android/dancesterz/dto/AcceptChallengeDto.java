package com.cvm.android.dancesterz.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Devalopment-1 on 18-01-2018.
 */

public class AcceptChallengeDto implements Serializable {
    private final static long serialVersionUID = 1L;
    protected UserSummary accepter;
    protected String desc;
    protected VideoDto video;
    protected Long responseId;
    protected BigInteger challengerVote;
    protected BigInteger accepterVote;
    protected Long challengeId;

    public UserSummary getAccepter() {
        return accepter;
    }

    public AcceptChallengeDto() {
    }

    public AcceptChallengeDto(UserSummary accepter, String desc, VideoDto video, Long responseId, BigInteger challengerVote, BigInteger accepterVote, Long challengeId) {
        this.accepter = accepter;
        this.desc = desc;
        this.video = video;
        this.responseId = responseId;
        this.challengerVote = challengerVote;
        this.accepterVote = accepterVote;
        this.challengeId = challengeId;
    }

    public void setAccepter(UserSummary accepter) {
        this.accepter = accepter;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public VideoDto getVideo() {
        return video;
    }

    public void setVideo(VideoDto video) {
        this.video = video;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public BigInteger getChallengerVote() {
        return challengerVote;
    }

    public void setChallengerVote(BigInteger challengerVote) {
        this.challengerVote = challengerVote;
    }
    public BigInteger getAccepterVote() {
        return accepterVote;
    }

    public void setAccepterVote(BigInteger accepterVote) {
        this.accepterVote = accepterVote;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }
}
