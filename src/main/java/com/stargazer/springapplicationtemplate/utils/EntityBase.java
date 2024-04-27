package com.stargazer.springapplicationtemplate.utils;

import java.time.LocalDateTime;

public class EntityBase {

    private long creatorId;

    private LocalDateTime creationTime = LocalDateTime.now();

    private long lastModifierId;

    private LocalDateTime lastModificationTime = LocalDateTime.now();

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public LocalDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }
}
