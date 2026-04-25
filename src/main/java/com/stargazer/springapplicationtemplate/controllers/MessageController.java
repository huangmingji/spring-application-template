package com.stargazer.springapplicationtemplate.controllers;

import java.util.List;

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
    public SendMessageResponse sendMessage(@RequestBody SendMessageRequest request) {
        return messageService.sendMessage(request);
    }

    @Operation(summary = "发送邮件")
    @PostMapping(value = "send/email")
    @PreAuthorize("hasAuthority('message:send:email') or hasRole('ADMIN')")
    public SendMessageResponse sendEmail(@RequestBody SendMessageRequest request) {
        request.setMessageType("email");
        return messageService.sendMessage(request);
    }

    @Operation(summary = "发送短信")
    @PostMapping(value = "send/sms")
    @PreAuthorize("hasAuthority('message:send:sms') or hasRole('ADMIN')")
    public SendMessageResponse sendSms(@RequestBody SendMessageRequest request) {
        request.setMessageType("sms");
        return messageService.sendMessage(request);
    }

    @Operation(summary = "批量发送消息")
    @PostMapping(value = "send/batch")
    @PreAuthorize("hasAuthority('message:send:batch') or hasRole('ADMIN')")
    public BatchSendResponse sendBatchMessage(@RequestBody BatchSendRequest request) {
        return messageService.sendBatchMessage(request);
    }

    @Operation(summary = "获取消息记录")
    @GetMapping(value = "record/{id}")
    @PreAuthorize("hasAuthority('message:read') or hasRole('ADMIN')")
    public MessageRecord getRecord(@PathVariable long id) {
        return messageService.getRecordById(id);
    }

    @Operation(summary = "获取消息记录列表")
    @GetMapping(value = "records")
    @PreAuthorize("hasAuthority('message:read:list') or hasRole('ADMIN')")
    public List<MessageRecord> getRecords(
            @RequestParam(required = false) String recipient,
            @RequestParam(required = false) String messageType,
            @RequestParam(required = false) String status) {
        if (recipient != null && !recipient.isEmpty()) {
            return messageService.getRecordsByRecipient(recipient);
        } else if (messageType != null && !messageType.isEmpty()) {
            return messageService.getRecordsByMessageType(messageType);
        } else if (status != null && !status.isEmpty()) {
            return messageService.getRecordsByStatus(status);
        } else {
            return messageService.getAllRecords();
        }
    }

    @Operation(summary = "创建消息模板")
    @PostMapping(value = "template")
    @PreAuthorize("hasAuthority('message:template:create') or hasRole('ADMIN')")
    public MessageTemplate createTemplate(@RequestBody MessageTemplate template) {
        return messageService.createTemplate(template);
    }

    @Operation(summary = "获取消息模板")
    @GetMapping(value = "template/{id}")
    @PreAuthorize("hasAuthority('message:template:read') or hasRole('ADMIN')")
    public MessageTemplate getTemplate(@PathVariable long id) {
        return messageService.getTemplateById(id);
    }

    @Operation(summary = "获取消息模板列表")
    @GetMapping(value = "templates")
    @PreAuthorize("hasAuthority('message:template:read') or hasRole('ADMIN')")
    public List<MessageTemplate> getTemplates(@RequestParam(required = false) String messageType) {
        if (messageType != null && !messageType.isEmpty()) {
            return messageService.getTemplatesByType(messageType);
        } else {
            return messageService.getAllTemplates();
        }
    }

    @Operation(summary = "更新消息模板")
    @PutMapping(value = "template/{id}")
    @PreAuthorize("hasAuthority('message:template:update') or hasRole('ADMIN')")
    public MessageTemplate updateTemplate(@PathVariable long id, @RequestBody MessageTemplate template) {
        return messageService.updateTemplate(id, template);
    }

    @Operation(summary = "删除消息模板")
    @DeleteMapping(value = "template/{id}")
    @PreAuthorize("hasAuthority('message:template:delete') or hasRole('ADMIN')")
    public void deleteTemplate(@PathVariable long id) {
        messageService.deleteTemplate(id);
    }
}