package com.apirest.services;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class FileStorageServices {

    private final GridFSBucket gridFSBucket;

    public GridFSFile getFile(String id){
        return gridFSBucket.find(new Document("_id", new ObjectId(id))).first();
    }

    @Autowired
    public FileStorageServices(MongoDatabaseFactory dbFactory){
        this.gridFSBucket = GridFSBuckets.create(dbFactory.getMongoDatabase("Images"));
    }

    public String uploadImage(MultipartFile file) throws Exception{

        GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(358400).metadata(
                new org.bson.Document("type", file.getContentType()));
        ObjectId fileId = gridFSBucket.uploadFromStream(
                file.getOriginalFilename(),
                file.getInputStream(),
                options);

        return fileId.toHexString();
    }

    public byte[] downloadImage(String id) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        gridFSBucket.downloadToStream(new ObjectId(id), outputStream);
        return outputStream.toByteArray();
    }

}
