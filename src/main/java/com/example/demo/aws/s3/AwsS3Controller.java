package com.example.demo.aws.s3;

import com.amazonaws.auth.BasicAWSCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;
    @PostMapping("/resource")
    public AwsS3 upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return awsS3Service.upload(multipartFile, "upload");
    }
    @DeleteMapping("/resource")
    public void remove(AwsS3 awsS3) {
        awsS3Service.remove(awsS3);
    }
}
