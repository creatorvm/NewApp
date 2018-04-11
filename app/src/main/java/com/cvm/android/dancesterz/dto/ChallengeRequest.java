package com.cvm.android.dancesterz.dto;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 18-01-2018.
 */

public class ChallengeRequest extends BaseRequest implements Serializable
{
    private final static long serialVersionUID = 1L;

    protected ChallengeDto challenge;

    public ChallengeDto getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDto challenge)
    {
        this.challenge = challenge;
    }
}
