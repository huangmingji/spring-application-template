package com.stargazer.springapplicationtemplate.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stargazer.springapplicationtemplate.domain.FileStorage;

@Mapper
public interface IFileStorageRepository {

    FileStorage findById(@Param("id") long id);

    List<FileStorage> findByUploaderId(@Param("uploaderId") long uploaderId);

    List<FileStorage> findByBucket(@Param("bucket") String bucket);

    List<FileStorage> findAll();

    int insert(FileStorage fileStorage);

    int update(FileStorage fileStorage);

    int deleteById(@Param("id") long id);

    long count();
}