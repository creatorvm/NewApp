package com.cvm.android.dancesterz.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Devalopment-1 on 12-01-2018.
 */

public class ChallegeResponse extends BaseResponse implements Serializable {

    private final static long serialVersionUID = 1L;
    protected List<ChallengeDto> challenges;
    protected Boolean acceptChallenge;

    public List<ChallengeDto> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<ChallengeDto> challenges) {
        this.challenges = challenges;
    }

    public Boolean getAcceptChallenge() {
        return acceptChallenge;
    }

    public void setAcceptChallenge(Boolean acceptChallenge) {
        this.acceptChallenge = acceptChallenge;
    }
}
