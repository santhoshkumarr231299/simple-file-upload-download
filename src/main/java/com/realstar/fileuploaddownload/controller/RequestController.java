package com.realstar.fileuploaddownload.controller;

import com.realstar.fileuploaddownload.model.FileModel;
import com.realstar.fileuploaddownload.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
public class RequestController {
    @Autowired
    private RequestService requestService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String message =  requestService.uploadFile(multipartFile);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity downloadFile(@PathVariable("fileName") String fileName) {
        Resource downloadResource = requestService.downloadFile(fileName);

        String headerValue = "attachment; filename=\"" + downloadResource.getFilename() + "\"";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(downloadResource);
    }

    @GetMapping("/getFiles")
    public ResponseEntity<List<FileModel>> downloadFile() {
        List<FileModel> fileModelList = requestService.getFiles();
        return ResponseEntity.ok(fileModelList);
    }
}
