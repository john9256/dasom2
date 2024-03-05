package com.dasom2.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final String EXTERNAL_FILE_PATH = "/image11/";

    public static String saveFile(MultipartFile file, String fileName) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(EXTERNAL_FILE_PATH + fileName + getFileExtension(file.getOriginalFilename()));
            Files.write(path, bytes);
            return path.toString();
        }
        return null;
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}

