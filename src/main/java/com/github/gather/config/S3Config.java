package com.github.gather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @org.springframework.beans.factory.annotation.Value("accessKeyId넣으세요.")
    private String accessKeyId;

    @org.springframework.beans.factory.annotation.Value("secretKey넣으세요.")
    private String secretKey;

    @org.springframework.beans.factory.annotation.Value("region넣으세요.")
    private String region;


    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
                .build();
    }
}
