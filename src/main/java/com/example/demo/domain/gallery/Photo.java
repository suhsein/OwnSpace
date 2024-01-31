package com.example.demo.domain.gallery;

import com.example.demo.aws.s3.AwsS3;
import lombok.Builder;
import lombok.Data;

@Data
public class Photo {
    private Long id;
    private AwsS3 awsS3;
    private String title;
    private String description;

    public Photo() {
    }

    @Builder
    public Photo(AwsS3 awsS3, String title, String description) {
        this.awsS3 = awsS3;
        this.title = title;
        this.description = description;
    }
}
