package com.daall.howtoeat.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j(topic = "S3Uploader")
public class S3Uploader {
    private final String bucketName = "howtoeat";
    private final String cloudFrontDomain = "https://cdn.howtoeat.ai.kr";  // CloudFront 설정된 도메인

    private final S3Client s3Client = S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(InstanceProfileCredentialsProvider.create())
            .build();

    public String upload(MultipartFile file, String dir, Long userId) throws IOException {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String key = dir + "/" + userId + "/" + UUID.randomUUID() + extension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return cloudFrontDomain + "/" + key;
    }

    public void delete(String url) {
        //url : "https://cdn.howtoeat.ai.kr/profiles/123/abc123.png";
        //key : "profiles/123/3ac7a9e3-....png" 형식
        if (url == null || url.trim().isEmpty()) {
            log.warn("삭제할 URL이 비어 있습니다.");
            return;
        }

        String key = url.replace(cloudFrontDomain + "/", "");

        if (!url.startsWith(cloudFrontDomain)) {
            log.debug("URL이 CloudFront 도메인으로 시작하지 않습니다: " + url);
            return;
        }

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

}
