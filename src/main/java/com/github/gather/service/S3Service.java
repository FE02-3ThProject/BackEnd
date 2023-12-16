package com.github.gather.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.File;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String key, File file) {
        String bucketName = "project2team";
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(), file.toPath());

        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
}
