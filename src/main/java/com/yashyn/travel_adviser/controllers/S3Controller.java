package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/upload-url")
    public ResponseEntity<String> generateUploadPhotoUrl(@RequestParam String folder) {
        return ResponseEntity.ok(s3Service.generatePresignedUrl(folder));
    }

    @GetMapping("/photo-url")
    public ResponseEntity<String> getPresignedPhotoUrl(@RequestParam String key,  @RequestParam String folder) {
        return ResponseEntity.ok(s3Service.generatePresignedGetUrl(key, folder));
    }
}