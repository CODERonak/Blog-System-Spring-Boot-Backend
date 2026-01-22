package com.project.BlogSystem.storage.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface GCPStorageService {
    String uploadFile(MultipartFile file) throws IOException;

    void deleteFile(String fileName) throws IOException;

    String getFileUrl(String fileName);
}
