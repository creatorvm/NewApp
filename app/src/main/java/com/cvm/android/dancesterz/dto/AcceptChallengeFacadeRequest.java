package com.cvm.android.dancesterz.dto;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 30-01-2018.
 */

public class AcceptChallengeFacadeRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    protected AcceptChallengeDto challenge;
    protected UserVideoDto userVideo;

    public AcceptChallengeFacadeRequest() {
    }
    public AcceptChallengeFacadeRequest(AcceptChallengeDto challenge, UserVideoDto userVideo) {
        this.challenge=challenge;
        this.userVideo= userVideo;
    }

    public AcceptChallengeDto getChallenge() {
        return this.challenge;
    }

    public void setChallenge(AcceptChallengeDto value) {
        this.challenge = value;
    }

    public UserVideoDto getUserVideo() {
        return this.userVideo;
    }

    public void setUserVideo(UserVideoDto value) {
        this.userVideo = value;
    }

}
