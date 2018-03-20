package com.cvm.android.dancesterz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {
    @JsonProperty
    private final static long serialVersionUID = 1L;

    @JsonProperty
    protected String requestSource;

    public BaseResponse() {
    }

    public BaseResponse(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

}
