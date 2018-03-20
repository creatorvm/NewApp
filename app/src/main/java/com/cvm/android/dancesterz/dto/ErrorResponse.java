package com.cvm.android.dancesterz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by prasadprabhakaran on 2/18/18.
 */

public class ErrorResponse {
    // HTTP Response Status Code
    @JsonProperty
    private Integer status;

    // General Error message
    @JsonProperty
    private String message;

    // Error Type
    @JsonProperty
    private String errorType;

    @JsonProperty
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date timestamp;

    @JsonProperty
    private String errorCode;

    public ErrorResponse(){

    }

    protected ErrorResponse(final String productCode, final String message, final String errorCode, Integer status) {
        this.message    = message;
        this.errorType  = errorCode;
        this.status     = status;
        this.timestamp  = new Date();
        this.errorCode  = productCode;
    }

    public static ErrorResponse of(final String message, final String errorCode) {
        return new ErrorResponse(null, message, errorCode, null);
    }

    public static ErrorResponse of(final String message, final String errorCode, Integer status) {
        return new ErrorResponse(null, message, errorCode, status);
    }

    public static ErrorResponse of(final String productCode, final String message, final String errorCode, Integer status) {
        return new ErrorResponse(productCode, message, errorCode, status);
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorType() {
        return errorType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", errorType='" + errorType + '\'' +
                ", timestamp=" + timestamp +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
