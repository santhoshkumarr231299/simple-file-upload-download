package com.realstar.fileuploaddownload.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FileUploadConfig {
    @Value("${real-star.servlet.multipart.file-upload-path}")
    private String destinationDirectory;
}
