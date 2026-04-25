package com.stargazer.springapplicationtemplate.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stargazer.springapplicationtemplate.config.StorageConfig;
import com.stargazer.springapplicationtemplate.domain.FileStorage;
import com.stargazer.springapplicationtemplate.repositories.IFileStorageRepository;
import com.stargazer.springapplicationtemplate.services.interfaces.IFileStorageService;
import com.stargazer.springapplicationtemplate.services.models.storage.FileInfoResponse;
import com.stargazer.springapplicationtemplate.services.models.storage.UploadResponse;
import com.stargazer.springapplicationtemplate.utils.SnowflakeIdGenerator;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataNotExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.ParameterInvalidException;

@Service
public class FileStorageService implements IFileStorageService {

    private final IFileStorageRepository fileStorageRepository;
    private final StorageConfig storageConfig;

    public FileStorageService(IFileStorageRepository fileStorageRepository, StorageConfig storageConfig) {
        this.fileStorageRepository = fileStorageRepository;
        this.storageConfig = storageConfig;
    }

    @Override
    public UploadResponse uploadFile(MultipartFile file, String bucket, long uploaderId) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new ParameterInvalidException("文件不能为空");
        }

        if (file.getSize() > storageConfig.getMaxFileSize()) {
            throw new ParameterInvalidException("文件大小超过限制，最大允许 " + storageConfig.getMaxFileSize() + " 字节");
        }

        String originalName = file.getOriginalFilename();
        String extension = getFileExtension(originalName);
        if (!storageConfig.isExtensionAllowed(extension)) {
            throw new ParameterInvalidException("不支持的文件类型，仅支持: " + storageConfig.getAllowedExtensions());
        }

        String storedName = UUID.randomUUID().toString() + "." + extension;
        String bucketPath = storageConfig.generateBucketPath(bucket);

        Path bucketDir = Paths.get(bucketPath);
        if (!Files.exists(bucketDir)) {
            Files.createDirectories(bucketDir);
        }

        String filePath = storageConfig.generateStoragePath(bucket, storedName);
        Path targetPath = Paths.get(filePath);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        FileStorage fileStorage = new FileStorage();
        fileStorage.setId(SnowflakeIdGenerator.getInstance().nextId());
        fileStorage.setBucket(bucket);
        fileStorage.setOriginalName(originalName);
        fileStorage.setStoredName(storedName);
        fileStorage.setFilePath(filePath);
        fileStorage.setFileSize(file.getSize());
        fileStorage.setMimeType(file.getContentType());
        fileStorage.setUploaderId(uploaderId);
        fileStorage.setCreatedAt(LocalDateTime.now());
        fileStorage.setUpdatedAt(LocalDateTime.now());

        fileStorageRepository.insert(fileStorage);

        UploadResponse response = new UploadResponse();
        response.setId(fileStorage.getId());
        response.setBucket(bucket);
        response.setOriginalName(originalName);
        response.setStoredName(storedName);
        response.setFileSize(file.getSize());
        response.setMimeType(file.getContentType());
        response.setCreatedAt(fileStorage.getCreatedAt());
        response.setDownloadUrl("/storage/" + fileStorage.getId());
        return response;
    }

    @Override
    public FileInfoResponse getFileInfo(long id) {
        FileStorage fileStorage = fileStorageRepository.findById(id);
        if (fileStorage == null) {
            throw new DataNotExistsException("文件不存在");
        }
        return toFileInfoResponse(fileStorage);
    }

    @Override
    public byte[] downloadFile(long id) throws Exception {
        FileStorage fileStorage = fileStorageRepository.findById(id);
        if (fileStorage == null) {
            throw new DataNotExistsException("文件不存在");
        }

        Path filePath = Paths.get(fileStorage.getFilePath());
        if (!Files.exists(filePath)) {
            throw new DataNotExistsException("文件已丢失");
        }

        return Files.readAllBytes(filePath);
    }

    @Override
    public void deleteFile(long id) throws Exception {
        FileStorage fileStorage = fileStorageRepository.findById(id);
        if (fileStorage == null) {
            throw new DataNotExistsException("文件不存在");
        }

        Path filePath = Paths.get(fileStorage.getFilePath());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        fileStorageRepository.deleteById(id);
    }

    @Override
    public List<FileInfoResponse> getFilesByUploader(long uploaderId) {
        List<FileStorage> files = fileStorageRepository.findByUploaderId(uploaderId);
        List<FileInfoResponse> responses = new ArrayList<>();
        for (FileStorage file : files) {
            responses.add(toFileInfoResponse(file));
        }
        return responses;
    }

    @Override
    public List<FileInfoResponse> getFilesByBucket(String bucket) {
        List<FileStorage> files = fileStorageRepository.findByBucket(bucket);
        List<FileInfoResponse> responses = new ArrayList<>();
        for (FileStorage file : files) {
            responses.add(toFileInfoResponse(file));
        }
        return responses;
    }

    @Override
    public List<FileInfoResponse> getAllFiles() {
        List<FileStorage> files = fileStorageRepository.findAll();
        List<FileInfoResponse> responses = new ArrayList<>();
        for (FileStorage file : files) {
            responses.add(toFileInfoResponse(file));
        }
        return responses;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    private FileInfoResponse toFileInfoResponse(FileStorage fileStorage) {
        FileInfoResponse response = new FileInfoResponse();
        response.setId(fileStorage.getId());
        response.setBucket(fileStorage.getBucket());
        response.setOriginalName(fileStorage.getOriginalName());
        response.setStoredName(fileStorage.getStoredName());
        response.setFileSize(fileStorage.getFileSize());
        response.setMimeType(fileStorage.getMimeType());
        response.setUploaderId(fileStorage.getUploaderId());
        response.setCreatedAt(fileStorage.getCreatedAt());
        response.setDownloadUrl("/storage/" + fileStorage.getId());
        return response;
    }
}