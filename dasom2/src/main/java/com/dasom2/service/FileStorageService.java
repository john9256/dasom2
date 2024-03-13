package com.dasom2.service;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileStorageService {

    private static final String EXTERNAL_FILE_PATH = "/image11/";

    public static String saveFile(MultipartFile file, String fileName) throws IOException {
        if (!file.isEmpty()) {
            
//          Path path = Paths.get(EXTERNAL_FILE_PATH + fileName + getFileExtension(file.getOriginalFilename()));
//          byte[] bytes = file.getBytes();
//          Files.write(path, bytes);
//          return path.toString();
        	String filePath = EXTERNAL_FILE_PATH + fileName;
            
            Thumbnails.of(file.getInputStream())
            .size(700, 700) // 원하는 크기 지정
            .outputFormat("jpg")
            .toFile(filePath);
            
            return filePath;
        }
        return null;
    }

//    private static String getFileExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf("."));
//    }
}

