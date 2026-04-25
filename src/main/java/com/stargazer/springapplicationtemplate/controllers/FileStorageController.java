package com.stargazer.springapplicationtemplate.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stargazer.springapplicationtemplate.domain.UserPrincipal;
import com.stargazer.springapplicationtemplate.services.interfaces.IFileStorageService;
import com.stargazer.springapplicationtemplate.services.models.storage.FileInfoResponse;
import com.stargazer.springapplicationtemplate.services.models.storage.UploadResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "文件存储")
@RestController
@RequestMapping(value = "storage")
public class FileStorageController {

    private final IFileStorageService fileStorageService;

    public FileStorageController(IFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Operation(summary = "上传文件")
    @PostMapping(value = "upload")
    @PreAuthorize("hasAuthority('storage:upload') or hasRole('ADMIN')")
    public UploadResponse upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "bucket", defaultValue = "default") String bucket) {
        long uploaderId = getCurrentUserId();
        return fileStorageService.uploadFile(file, bucket, uploaderId);
    }

    @Operation(summary = "获取文件信息")
    @GetMapping(value = "{id}/info")
    @PreAuthorize("hasAuthority('storage:read') or hasRole('ADMIN')")
    public FileInfoResponse getFileInfo(@PathVariable long id) {
        return fileStorageService.getFileInfo(id);
    }

    @Operation(summary = "下载文件")
    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('storage:read') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> download(@PathVariable long id) {
        FileInfoResponse fileInfo = fileStorageService.getFileInfo(id);
        byte[] fileData = fileStorageService.downloadFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileInfo.getMimeType()));
        headers.setContentDispositionFormData("attachment", fileInfo.getOriginalName());
        headers.setContentLength(fileInfo.getFileSize());

        return ResponseEntity.ok().headers(headers).body(fileData);
    }

    @Operation(summary = "预览文件")
    @GetMapping(value = "{id}/preview")
    @PreAuthorize("hasAuthority('storage:read') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> preview(@PathVariable long id) {
        FileInfoResponse fileInfo = fileStorageService.getFileInfo(id);
        byte[] fileData = fileStorageService.downloadFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileInfo.getMimeType()));
        headers.setContentLength(fileInfo.getFileSize());

        return ResponseEntity.ok().headers(headers).body(fileData);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('storage:delete') or hasRole('ADMIN')")
    public void delete(@PathVariable long id) {
        fileStorageService.deleteFile(id);
    }

    @Operation(summary = "获取文件列表")
    @GetMapping(value = "list")
    @PreAuthorize("hasAuthority('storage:read:list') or hasRole('ADMIN')")
    public List<FileInfoResponse> listFiles(
            @RequestParam(value = "uploaderId", required = false) Long uploaderId,
            @RequestParam(value = "bucket", required = false) String bucket) {
        if (uploaderId != null) {
            return fileStorageService.getFilesByUploader(uploaderId);
        } else if (bucket != null) {
            return fileStorageService.getFilesByBucket(bucket);
        } else {
            return fileStorageService.getAllFiles();
        }
    }

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return ((UserPrincipal) authentication.getPrincipal()).getId();
        }
        return 0;
    }
}