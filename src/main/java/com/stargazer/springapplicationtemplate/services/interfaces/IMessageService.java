package com.stargazer.springapplicationtemplate.services.interfaces;

import java.util.List;

import com.stargazer.springapplicationtemplate.domain.MessageRecord;
import com.stargazer.springapplicationtemplate.domain.MessageTemplate;
import com.stargazer.springapplicationtemplate.services.models.message.BatchSendRequest;
import com.stargazer.springapplicationtemplate.services.models.message.BatchSendResponse;
import com.stargazer.springapplicationtemplate.services.models.message.SendMessageRequest;
import com.stargazer.springapplicationtemplate.services.models.message.SendMessageResponse;

public interface IMessageService {

    SendMessageResponse sendMessage(SendMessageRequest request) throws Exception;

    BatchSendResponse sendBatchMessage(BatchSendRequest request) throws Exception;

    MessageRecord getRecordById(long id);

    List<MessageRecord> getRecordsByRecipient(String recipient);

    List<MessageRecord> getRecordsByMessageType(String messageType);

    List<MessageRecord> getRecordsByStatus(String status);

    List<MessageRecord> getAllRecords();

    MessageTemplate getTemplateById(long id);

    MessageTemplate getTemplateByCode(String templateCode);

    MessageTemplate createTemplate(MessageTemplate template);

    MessageTemplate updateTemplate(long id, MessageTemplate template);

    void deleteTemplate(long id);

    List<MessageTemplate> getTemplatesByType(String messageType);

    List<MessageTemplate> getAllTemplates();
}