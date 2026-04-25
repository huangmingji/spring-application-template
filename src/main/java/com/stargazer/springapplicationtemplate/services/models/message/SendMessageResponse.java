package com.stargazer.springapplicationtemplate.services.models.message;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SendMessageResponse implements Serializable {

    private long recordId;

    private String status;

    private String message;

    private LocalDateTime sendTime;

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}