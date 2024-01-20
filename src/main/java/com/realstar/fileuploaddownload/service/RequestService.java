package com.realstar.fileuploaddownload.service;

import com.realstar.fileuploaddownload.config.FileUploadConfig;
import com.realstar.fileuploaddownload.model.FileModel;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RequestService {
    @Autowired
    private FileUploadConfig fileUploadConfig;
    public String uploadFile(MultipartFile multipartFile) {
        try {
            @Cleanup InputStream inputStream = multipartFile.getInputStream();
            Path destinationPath = Paths.get(fileUploadConfig.getDestinationDirectory()).resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(inputStream, destinationPath);
        } catch (IOException ioException) {
            return "Failed Uploading the File";
        } catch (NullPointerException nullPointerException) {
            return "Failed Reading the File";
        }
        return "File uploaded successfully";
    }

    public Resource downloadFile(String fileName) {
        try {
            //TODO : santize fileName, check the fileName exists or not
            Path downloadDir = Paths.get(fileUploadConfig.getDestinationDirectory() + File.separator + fileName);
            return new UrlResource(downloadDir.toUri());
        } catch (Exception e) {
            return null;
        }
    }

    public List<FileModel> getFiles() {
        File file = new File(fileUploadConfig.getDestinationDirectory());
        String[] files = file.list();
        if(files == null) {
            return new ArrayList<>();
        }
        List<FileModel> fileModelList = new ArrayList<>();
        for(String downloadFile : files) {
            FileModel fileModel = new FileModel();
            fileModel.setFileName(downloadFile);
            fileModelList.add(fileModel);
        }
        return fileModelList;
    }
}
