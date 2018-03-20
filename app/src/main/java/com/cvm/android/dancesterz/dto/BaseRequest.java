package com.cvm.android.dancesterz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Devalopment-1 on 01-01-2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest implements Serializable {
    @JsonProperty
    private final static long serialVersionUID = 1L;
    @JsonProperty
    protected String requestSource;
    @JsonProperty
    protected String token;
    @JsonProperty
    protected String transactionId;

    public BaseRequest(String requestSource, String token) {
        this.requestSource = requestSource;
        this.token = token;
    }

    public BaseRequest() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
