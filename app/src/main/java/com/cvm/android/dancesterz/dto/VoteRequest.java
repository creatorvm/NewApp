package com.cvm.android.dancesterz.dto;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 09-02-2018.
 */

public class VoteRequest extends BaseRequest implements Serializable {
    private final static long serialVersionUID = 1L;
    protected VoteDto vote;

    public VoteDto getVote() {
        return vote;
    }

    public void setVote(VoteDto vote) {
        this.vote = vote;
    }
}
