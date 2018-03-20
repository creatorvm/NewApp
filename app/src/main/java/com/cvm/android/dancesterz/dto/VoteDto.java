package com.cvm.android.dancesterz.dto;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 27-01-2018.
 */

public class VoteDto implements Serializable {

    private final static long serialVersionUID = 1L;

    protected Long voterId;

    protected Long candidateId;

    protected Long challengeId;

    protected Long challengeResponseId;

    protected Long ownerVote;

    protected Long responseVote;
    Long userId;

    public VoteDto(Long voterId, Long candidateId, Long challengeId, Long challengeResponseId, Long ownerVote, Long responseVote, Long userId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.challengeId = challengeId;
        this.challengeResponseId = challengeResponseId;
        this.ownerVote = ownerVote;
        this.responseVote = responseVote;
        this.userId = userId;
    }

    public VoteDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public VoteDto(Long voterId, Long candidateId, Long challengeId, Long challengeResponseId, Long ownerVote, Long responseVote) {
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.challengeId = challengeId;

        this.challengeResponseId = challengeResponseId;
        this.ownerVote = ownerVote;
        this.responseVote = responseVote;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public Long getChallengeResponseId() {
        return challengeResponseId;
    }

    public void setChallengeResponseId(Long challengeResponseId) {
        this.challengeResponseId = challengeResponseId;
    }

    public Long getOwnerVote() {
        return ownerVote;
    }

    public void setOwnerVote(Long ownerVote) {
        this.ownerVote = ownerVote;
    }

    public Long getResponseVote() {
        return responseVote;
    }

    public void setResponseVote(Long responseVote) {
        this.responseVote = responseVote;
    }
}
