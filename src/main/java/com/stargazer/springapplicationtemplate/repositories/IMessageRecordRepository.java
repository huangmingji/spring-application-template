package com.stargazer.springapplicationtemplate.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stargazer.springapplicationtemplate.domain.MessageRecord;

@Mapper
public interface IMessageRecordRepository {

    MessageRecord findById(@Param("id") long id);

    List<MessageRecord> findByRecipient(@Param("recipient") String recipient);

    List<MessageRecord> findByMessageType(@Param("messageType") String messageType);

    List<MessageRecord> findByStatus(@Param("status") String status);

    List<MessageRecord> findAll();

    int insert(MessageRecord record);

    int update(MessageRecord record);

    int deleteById(@Param("id") long id);

    long count();
}