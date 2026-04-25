package com.stargazer.springapplicationtemplate.services.models.message;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BatchSendResponse implements Serializable {

    private int total;

    private int success;

    private int failed;

    private String message;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}