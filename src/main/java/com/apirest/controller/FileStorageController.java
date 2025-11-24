package com.apirest.controller;

import com.apirest.model.FileMetaData;
import com.apirest.services.FileStorageServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FileStorageController {

    @Autowired
    private final Path fileStorageLocation;

    public FileStorageController(FileStorageServices fileStorageServices) {
        this.fileStorageLocation = (Path) fileStorageServices;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);


            FileMetaData metaData = new FileMetaData();
            metaData.setFileName(fileName);
            metaData.setContentType(file.getContentType());
            metaData.setSize(file.getSize());
            metaData.setPath(targetLocation.toString());



            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/files/download").path(fileName).toUriString();
            return ResponseEntity.ok("Upload completed! Download link: " + fileDownloadUri);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("No image was uploaded");
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null) contentType = "application/octet-stream";
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> fileNames = Files.list(fileStorageLocation).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());

        return ResponseEntity.ok(fileNames);
    }
}
