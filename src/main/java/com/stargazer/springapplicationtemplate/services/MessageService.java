package com.stargazer.springapplicationtemplate.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stargazer.springapplicationtemplate.domain.MessageRecord;
import com.stargazer.springapplicationtemplate.domain.MessageTemplate;
import com.stargazer.springapplicationtemplate.repositories.IMessageRecordRepository;
import com.stargazer.springapplicationtemplate.repositories.IMessageTemplateRepository;
import com.stargazer.springapplicationtemplate.services.interfaces.IMessageService;
import com.stargazer.springapplicationtemplate.services.models.message.BatchSendRequest;
import com.stargazer.springapplicationtemplate.services.models.message.BatchSendResponse;
import com.stargazer.springapplicationtemplate.services.models.message.SendMessageRequest;
import com.stargazer.springapplicationtemplate.services.models.message.SendMessageResponse;
import com.stargazer.springapplicationtemplate.utils.SnowflakeIdGenerator;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataAlreadyExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataNotExistsException;

@Service
public class MessageService implements IMessageService {

    private final IMessageRecordRepository messageRecordRepository;
    private final IMessageTemplateRepository messageTemplateRepository;
    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;

    public MessageService(
            IMessageRecordRepository messageRecordRepository,
            IMessageTemplateRepository messageTemplateRepository,
            JavaMailSender mailSender) {
        this.messageRecordRepository = messageRecordRepository;
        this.messageTemplateRepository = messageTemplateRepository;
        this.mailSender = mailSender;
        this.objectMapper = new ObjectMapper();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public SendMessageResponse sendMessage(SendMessageRequest request) throws Exception {
        MessageRecord record = new MessageRecord();
        record.setId(SnowflakeIdGenerator.getInstance().nextId());
        record.setMessageType(request.getMessageType());
        record.setRecipient(request.getRecipient());
        record.setStatus(MessageRecord.STATUS_PENDING);
        record.setSendTime(LocalDateTime.now());
        record.setCreationTime(LocalDateTime.now());
        record.setLastModificationTime(LocalDateTime.now());

        String content = request.getContent();
        String subject = request.getSubject();

        if (request.getTemplateCode() != null && !request.getTemplateCode().isEmpty()) {
            MessageTemplate template = messageTemplateRepository.findByCode(request.getTemplateCode());
            if (template == null) {
                throw new DataNotExistsException("消息模板不存在");
            }
            if (!template.isEnabled()) {
                throw new DataNotExistsException("消息模板已禁用");
            }
            record.setTemplateId(template.getTemplateCode());
            if (request.getTemplateParams() != null) {
                record.setTemplateParams(objectMapper.writeValueAsString(request.getTemplateParams()));
            }
            if (template.getContent() != null) {
                content = replaceTemplate(template.getContent(), request.getTemplateParams());
            }
            if (template.getSubject() != null) {
                subject = replaceTemplate(template.getSubject(), request.getTemplateParams());
            }
        }

        record.setSubject(subject);
        record.setContent(content);

        try {
            if (MessageRecord.TYPE_EMAIL.equals(request.getMessageType())) {
                sendEmail(request.getRecipient(), subject, content);
            } else if (MessageRecord.TYPE_SMS.equals(request.getMessageType())) {
                sendSms(request.getRecipient(), content);
            } else {
                throw new IllegalArgumentException("不支持的消息类型: " + request.getMessageType());
            }
            record.setStatus(MessageRecord.STATUS_SENT);
        } catch (Exception e) {
            record.setStatus(MessageRecord.STATUS_FAILED);
            record.setErrorMessage(e.getMessage());
            throw e;
        }

        messageRecordRepository.insert(record);

        SendMessageResponse response = new SendMessageResponse();
        response.setRecordId(record.getId());
        response.setStatus(record.getStatus());
        response.setMessage(record.getStatus().equals(MessageRecord.STATUS_SENT) ? "发送成功" : "发送失败");
        response.setSendTime(record.getSendTime());
        return response;
    }

    @Override
    public BatchSendResponse sendBatchMessage(BatchSendRequest request) throws Exception {
        BatchSendResponse response = new BatchSendResponse();
        int success = 0;
        int failed = 0;

        for (String recipient : request.getRecipients()) {
            try {
                SendMessageRequest singleRequest = new SendMessageRequest();
                singleRequest.setMessageType(request.getMessageType());
                singleRequest.setRecipient(recipient);
                singleRequest.setSubject(request.getSubject());
                singleRequest.setContent(request.getContent());
                singleRequest.setTemplateCode(request.getTemplateCode());
                sendMessage(singleRequest);
                success++;
            } catch (Exception e) {
                failed++;
            }
        }

        response.setTotal(request.getRecipients().size());
        response.setSuccess(success);
        response.setFailed(failed);
        response.setMessage("批量发送完成：成功 " + success + "，失败 " + failed);
        return response;
    }

    @Async
    public CompletableFuture<SendMessageResponse> sendMessageAsync(SendMessageRequest request) {
        try {
            SendMessageResponse response = sendMessage(request);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            SendMessageResponse response = new SendMessageResponse();
            response.setStatus(MessageRecord.STATUS_FAILED);
            response.setMessage("异步发送失败: " + e.getMessage());
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public MessageRecord getRecordById(long id) {
        MessageRecord record = messageRecordRepository.findById(id);
        if (record == null) {
            throw new DataNotExistsException("消息记录不存在");
        }
        return record;
    }

    @Override
    public List<MessageRecord> getRecordsByRecipient(String recipient) {
        return messageRecordRepository.findByRecipient(recipient);
    }

    @Override
    public List<MessageRecord> getRecordsByMessageType(String messageType) {
        return messageRecordRepository.findByMessageType(messageType);
    }

    @Override
    public List<MessageRecord> getRecordsByStatus(String status) {
        return messageRecordRepository.findByStatus(status);
    }

    @Override
    public List<MessageRecord> getAllRecords() {
        return messageRecordRepository.findAll();
    }

    @Override
    public MessageTemplate getTemplateById(long id) {
        MessageTemplate template = messageTemplateRepository.findById(id);
        if (template == null) {
            throw new DataNotExistsException("消息模板不存在");
        }
        return template;
    }

    @Override
    public MessageTemplate getTemplateByCode(String templateCode) {
        MessageTemplate template = messageTemplateRepository.findByCode(templateCode);
        if (template == null) {
            throw new DataNotExistsException("消息模板不存在");
        }
        return template;
    }

    @Override
    public MessageTemplate createTemplate(MessageTemplate template) {
        if (messageTemplateRepository.existsByCode(template.getTemplateCode())) {
            throw new DataAlreadyExistsException("模板编码已存在");
        }
        template.setId(SnowflakeIdGenerator.getInstance().nextId());
        messageTemplateRepository.insert(template);
        return template;
    }

    @Override
    public MessageTemplate updateTemplate(long id, MessageTemplate template) {
        MessageTemplate existing = messageTemplateRepository.findById(id);
        if (existing == null) {
            throw new DataNotExistsException("消息模板不存在");
        }
        existing.setTemplateCode(template.getTemplateCode());
        existing.setTemplateName(template.getTemplateName());
        existing.setMessageType(template.getMessageType());
        existing.setSubject(template.getSubject());
        existing.setContent(template.getContent());
        existing.setEnabled(template.isEnabled());
        existing.setDescription(template.getDescription());
        messageTemplateRepository.update(existing);
        return existing;
    }

    @Override
    public void deleteTemplate(long id) {
        messageTemplateRepository.deleteById(id);
    }

    @Override
    public List<MessageTemplate> getTemplatesByType(String messageType) {
        return messageTemplateRepository.findByMessageType(messageType);
    }

    @Override
    public List<MessageTemplate> getAllTemplates() {
        return messageTemplateRepository.findAll();
    }

    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    private void sendSms(String phoneNumber, String content) {
        // SMS sending logic - implement based on your SMS provider
        // Example: Aliyun SMS SDK
        // This is a placeholder - you need to implement the actual SMS sending logic
        throw new UnsupportedOperationException("SMS sending not implemented. Please configure SMS provider.");
    }

    private String replaceTemplate(String template, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return template;
        }
        StringSubstitutor substitutor = new StringSubstitutor(params, "${", "}");
        return substitutor.replace(template);
    }
}