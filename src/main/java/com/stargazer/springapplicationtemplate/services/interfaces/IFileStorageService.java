package com.stargazer.springapplicationtemplate.services.interfaces;

import java.util.List;

import com.stargazer.springapplicationtemplate.services.models.storage.FileInfoResponse;
import com.stargazer.springapplicationtemplate.services.models.storage.UploadResponse;

public interface IFileStorageService {

    UploadResponse uploadFile(org.springframework.web.multipart.MultipartFile file, String bucket, long uploaderId) throws Exception;

    FileInfoResponse getFileInfo(long id);

    byte[] downloadFile(long id) throws Exception;

    void deleteFile(long id) throws Exception;

    List<FileInfoResponse> getFilesByUploader(long uploaderId);

    List<FileInfoResponse> getFilesByBucket(String bucket);

    List<FileInfoResponse> getAllFiles();
}