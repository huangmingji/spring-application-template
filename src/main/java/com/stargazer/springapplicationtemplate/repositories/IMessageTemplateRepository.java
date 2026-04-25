package com.stargazer.springapplicationtemplate.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stargazer.springapplicationtemplate.domain.MessageTemplate;

@Mapper
public interface IMessageTemplateRepository {

    MessageTemplate findById(@Param("id") long id);

    MessageTemplate findByCode(@Param("templateCode") String templateCode);

    List<MessageTemplate> findByMessageType(@Param("messageType") String messageType);

    List<MessageTemplate> findByEnabled(@Param("enabled") boolean enabled);

    List<MessageTemplate> findAll();

    int insert(MessageTemplate template);

    int update(MessageTemplate template);

    int deleteById(@Param("id") long id);

    boolean existsByCode(@Param("templateCode") String templateCode);
}