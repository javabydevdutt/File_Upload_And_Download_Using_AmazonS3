package com.devdutt.uploads.cloud.controller;

import com.devdutt.uploads.cloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("/uploads")
    public ResponseEntity<String> uploadsFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<String>(service.uploadsFile(file), HttpStatus.OK);
    }

    @GetMapping("/downloads/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFIle(@PathVariable String fileName) {
        byte[] downloadFile = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(downloadFile);
        return ResponseEntity
                .ok()
                .contentLength(downloadFile.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<String>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
