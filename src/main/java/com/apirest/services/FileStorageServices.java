package com.apirest.services;

import com.apirest.model.FileMetaData;
import com.apirest.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileStorageServices {

    @Autowired
    private FileRepo fileRepo;

    public List<FileMetaData> findAll(){
        return fileRepo.findAll();
    }

    public FileMetaData findById(String id){
        return fileRepo.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public FileMetaData saveImage(FileMetaData fileMetaData){
        return fileRepo.save(fileMetaData);
    }

    public void delete(String id){
        fileRepo.deleteById(id);
    }

//    private String uploadDir;
//
//    public String getUploadDir() {
//        return uploadDir;
//    }
//
//    public void setUploadDir(String uploadDir) {
//        this.uploadDir = uploadDir;
//    }



}
