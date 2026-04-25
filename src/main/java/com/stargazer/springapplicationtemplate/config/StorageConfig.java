package com.stargazer.springapplicationtemplate.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@Configuration
public class StorageConfig {

    @Value("${storage.local.base-path}")
    private String basePath;

    @Value("${storage.local.max-file-size}")
    private long maxFileSize;

    @Value("${storage.local.allowed-extensions}")
    private String allowedExtensions;

    private List<String> allowedExtensionList;

    @PostConstruct
    public void init() throws IOException {
        Path uploadPath = Paths.get(basePath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        allowedExtensionList = Arrays.asList(allowedExtensions.split(","));
    }

    public String getBasePath() {
        return basePath;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public List<String> getAllowedExtensions() {
        return allowedExtensionList;
    }

    public boolean isExtensionAllowed(String extension) {
        return allowedExtensionList.contains(extension.toLowerCase());
    }

    public String generateStoragePath(String bucket, String storedName) {
        return basePath + "/" + bucket + "/" + storedName;
    }

    public String generateBucketPath(String bucket) {
        return basePath + "/" + bucket;
    }
}