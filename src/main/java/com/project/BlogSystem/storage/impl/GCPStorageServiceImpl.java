package com.project.BlogSystem.storage.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.project.BlogSystem.storage.interfaces.GCPStorageService;

/*

GCPStorageServiceImpl implements GCPStorageService, which icludes methods:

* String uploadFile(MultipartFile file): uploads the multipart file to GCP storage
* void deleteFile(String fileName): deletes the multipart file present in the GCP storage
* String getFileUrl(String fileName): gets file url stored in the GCP storage 

*/

@Service
public class GCPStorageServiceImpl implements GCPStorageService {

    private final Storage storage;
    private final String bucketName;

    public GCPStorageServiceImpl(Storage storage, @Value("${spring.cloud.gcp.storage.bucket}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());
        return fileName;
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        storage.delete(blobId);
    }

    @Override
    public String getFileUrl(String fileName) {
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }

}
