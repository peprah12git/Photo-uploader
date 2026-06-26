package org.example.photouploader.service;

import lombok.RequiredArgsConstructor;
import org.example.photouploader.model.Photo;
import org.example.photouploader.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final S3Client s3Client;
    private final PhotoRepository photoRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;

    public Photo uploadPhoto(MultipartFile file, String description) throws IOException {
        String s3Key = "photos/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

        String cloudFrontUrl = "https://" + cloudFrontDomain + "/" + s3Key;

        Photo photo = new Photo();
        photo.setFileName(file.getOriginalFilename());
        photo.setS3Key(s3Key);
        photo.setDescription(description);
        photo.setCloudFrontUrl(cloudFrontUrl);

        return photoRepository.save(photo);
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
}