package com.stargazer.springapplicationtemplate.services.models.storage;

import org.springframework.web.multipart.MultipartFile;

public class UploadRequest {

    private MultipartFile file;

    private String bucket;

    private long uploaderId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(long uploaderId) {
        this.uploaderId = uploaderId;
    }
}