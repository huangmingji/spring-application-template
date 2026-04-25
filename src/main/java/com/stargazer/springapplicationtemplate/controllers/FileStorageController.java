package com.stargazer.springapplicationtemplate.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    public ResponseEntity<UploadResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "bucket", defaultValue = "default") String bucket,
            HttpServletRequest request) {
        try {
            long uploaderId = getCurrentUserId();
            UploadResponse response = fileStorageService.uploadFile(file, bucket, uploaderId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "获取文件信息")
    @GetMapping(value = "{id}/info")
    @PreAuthorize("hasAuthority('storage:read') or hasRole('ADMIN')")
    public ResponseEntity<FileInfoResponse> getFileInfo(@PathVariable long id) {
        try {
            FileInfoResponse response = fileStorageService.getFileInfo(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "下载文件")
    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('storage:read') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> download(@PathVariable long id) {
        try {
            FileInfoResponse fileInfo = fileStorageService.getFileInfo(id);
            byte[] fileData = fileStorageService.downloadFile(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileInfo.getMimeType()));
            headers.setContentDispositionFormData("attachment", fileInfo.getOriginalName());
            headers.setContentLength(fileInfo.getFileSize());

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "预览文件")
    @GetMapping(value = "{id}/preview")
    @PreAuthorize("hasAuthority('storage:read') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> preview(@PathVariable long id) {
        try {
            FileInfoResponse fileInfo = fileStorageService.getFileInfo(id);
            byte[] fileData = fileStorageService.downloadFile(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileInfo.getMimeType()));
            headers.setContentLength(fileInfo.getFileSize());

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "删除文件")
    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('storage:delete') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            fileStorageService.deleteFile(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "获取文件列表")
    @GetMapping(value = "list")
    @PreAuthorize("hasAuthority('storage:read:list') or hasRole('ADMIN')")
    public ResponseEntity<List<FileInfoResponse>> listFiles(
            @RequestParam(value = "uploaderId", required = false) Long uploaderId,
            @RequestParam(value = "bucket", required = false) String bucket) {
        List<FileInfoResponse> files;
        if (uploaderId != null) {
            files = fileStorageService.getFilesByUploader(uploaderId);
        } else if (bucket != null) {
            files = fileStorageService.getFilesByBucket(bucket);
        } else {
            files = fileStorageService.getAllFiles();
        }
        return ResponseEntity.ok(files);
    }

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return ((UserPrincipal) authentication.getPrincipal()).getId();
        }
        return 0;
    }
}