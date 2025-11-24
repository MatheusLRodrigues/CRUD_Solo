package com.apirest.repository;

import com.apirest.model.FileMetaData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends MongoRepository<FileMetaData, String>{
}