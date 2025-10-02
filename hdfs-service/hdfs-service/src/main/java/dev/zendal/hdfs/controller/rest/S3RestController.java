package dev.zendal.hdfs.controller.rest;

import dev.zendal.hdfs.service.HDFSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(WebConstants.FULL_WEB + "/s3")
@RequiredArgsConstructor
public class S3RestController {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private final HDFSService hdfsService;


    private final String bucket = "hack-2025";

    @PostMapping("/multipart/create")
    public Map<String, String> createMultipart(@RequestParam String key) {
        CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(request);
        return Map.of("uploadId", response.uploadId());
    }

    @GetMapping("/multipart/presign")
    public Map<String, String> presignPart(@RequestParam String key,
                                           @RequestParam String uploadId,
                                           @RequestParam int partNumber) {
        UploadPartRequest partRequest = UploadPartRequest.builder()
                .bucket(bucket)
                .key(key)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build();

        PresignedUploadPartRequest presignedRequest = this.s3Presigner.presignUploadPart(b -> b
                .signatureDuration(Duration.ofHours(1))
                .uploadPartRequest(partRequest));

        return Map.of("url", presignedRequest.url().toString());
    }

    @PostMapping("/multipart/complete")
    public void completeMultipart(@RequestParam String key,
                                  @RequestParam String uploadId,
                                  @RequestBody List<Map<String, String>> parts) {
        List<CompletedPart> completedParts = parts.stream()
                .map(p -> CompletedPart.builder()
                        .partNumber(Integer.parseInt(p.get("partNumber")))
                        .eTag(p.get("eTag"))
                        .build())
                .toList();

        CompletedMultipartUpload completedUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();

        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.builder()
                .bucket(bucket)
                .key(key)
                .uploadId(uploadId)
                .multipartUpload(completedUpload)
                .build();

        s3Client.completeMultipartUpload(request);

        this.hdfsService.moveFromS3(key);
    }
}
