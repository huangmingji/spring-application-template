package com.stargazer.springapplicationtemplate.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.springapplicationtemplate.domain.MessageRecord;
import com.stargazer.springapplicationtemplate.domain.MessageTemplate;
import com.stargazer.springapplicationtemplate.services.interfaces.IMessageService;
import com.stargazer.springapplicationtemplate.services.models.message.BatchSendRequest;
import com.stargazer.springapplicationtemplate.services.models.message.BatchSendResponse;
import com.stargazer.springapplicationtemplate.services.models.message.SendMessageRequest;
import com.stargazer.springapplicationtemplate.services.models.message.SendMessageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "消息管理")
@RestController
@RequestMapping(value = "message")
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "发送消息")
    @PostMapping(value = "send")
    @PreAuthorize("hasAuthority('message:send') or hasRole('ADMIN')")
    public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest request) {
        try {
            SendMessageResponse response = messageService.sendMessage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "发送邮件")
    @PostMapping(value = "send/email")
    @PreAuthorize("hasAuthority('message:send:email') or hasRole('ADMIN')")
    public ResponseEntity<SendMessageResponse> sendEmail(@RequestBody SendMessageRequest request) {
        try {
            request.setMessageType("email");
            SendMessageResponse response = messageService.sendMessage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "发送短信")
    @PostMapping(value = "send/sms")
    @PreAuthorize("hasAuthority('message:send:sms') or hasRole('ADMIN')")
    public ResponseEntity<SendMessageResponse> sendSms(@RequestBody SendMessageRequest request) {
        try {
            request.setMessageType("sms");
            SendMessageResponse response = messageService.sendMessage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "批量发送消息")
    @PostMapping(value = "send/batch")
    @PreAuthorize("hasAuthority('message:send:batch') or hasRole('ADMIN')")
    public ResponseEntity<BatchSendResponse> sendBatchMessage(@RequestBody BatchSendRequest request) {
        try {
            BatchSendResponse response = messageService.sendBatchMessage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "获取消息记录")
    @GetMapping(value = "record/{id}")
    @PreAuthorize("hasAuthority('message:read') or hasRole('ADMIN')")
    public ResponseEntity<MessageRecord> getRecord(@PathVariable long id) {
        try {
            MessageRecord record = messageService.getRecordById(id);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "获取消息记录列表")
    @GetMapping(value = "records")
    @PreAuthorize("hasAuthority('message:read:list') or hasRole('ADMIN')")
    public ResponseEntity<List<MessageRecord>> getRecords(
            @RequestParam(required = false) String recipient,
            @RequestParam(required = false) String messageType,
            @RequestParam(required = false) String status) {
        List<MessageRecord> records;
        if (recipient != null && !recipient.isEmpty()) {
            records = messageService.getRecordsByRecipient(recipient);
        } else if (messageType != null && !messageType.isEmpty()) {
            records = messageService.getRecordsByMessageType(messageType);
        } else if (status != null && !status.isEmpty()) {
            records = messageService.getRecordsByStatus(status);
        } else {
            records = messageService.getAllRecords();
        }
        return ResponseEntity.ok(records);
    }

    @Operation(summary = "创建消息模板")
    @PostMapping(value = "template")
    @PreAuthorize("hasAuthority('message:template:create') or hasRole('ADMIN')")
    public ResponseEntity<MessageTemplate> createTemplate(@RequestBody MessageTemplate template) {
        try {
            MessageTemplate created = messageService.createTemplate(template);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "获取消息模板")
    @GetMapping(value = "template/{id}")
    @PreAuthorize("hasAuthority('message:template:read') or hasRole('ADMIN')")
    public ResponseEntity<MessageTemplate> getTemplate(@PathVariable long id) {
        try {
            MessageTemplate template = messageService.getTemplateById(id);
            return ResponseEntity.ok(template);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "获取消息模板列表")
    @GetMapping(value = "templates")
    @PreAuthorize("hasAuthority('message:template:read') or hasRole('ADMIN')")
    public ResponseEntity<List<MessageTemplate>> getTemplates(
            @RequestParam(required = false) String messageType) {
        List<MessageTemplate> templates;
        if (messageType != null && !messageType.isEmpty()) {
            templates = messageService.getTemplatesByType(messageType);
        } else {
            templates = messageService.getAllTemplates();
        }
        return ResponseEntity.ok(templates);
    }

    @Operation(summary = "更新消息模板")
    @PutMapping(value = "template/{id}")
    @PreAuthorize("hasAuthority('message:template:update') or hasRole('ADMIN')")
    public ResponseEntity<MessageTemplate> updateTemplate(
            @PathVariable long id,
            @RequestBody MessageTemplate template) {
        try {
            MessageTemplate updated = messageService.updateTemplate(id, template);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "删除消息模板")
    @DeleteMapping(value = "template/{id}")
    @PreAuthorize("hasAuthority('message:template:delete') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTemplate(@PathVariable long id) {
        messageService.deleteTemplate(id);
        return ResponseEntity.ok().build();
    }
}