package com.apirest.controller;

import com.apirest.services.FileStorageServices;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileStorageController {

    @Autowired
    private FileStorageServices fileStorageServices;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String id = fileStorageServices.uploadImage(file);
            return ResponseEntity.ok("Image saved with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable String id) {
        try {
            GridFSFile file = fileStorageServices.getFile(id);

            if (file == null) {
                return ResponseEntity.badRequest().body("No Image found");
            }
            byte[] data = fileStorageServices.downloadImage(id);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").contentType(MediaType.parseMediaType(file.getMetadata().getString("type"))).body(data);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}