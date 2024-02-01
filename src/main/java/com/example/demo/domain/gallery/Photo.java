package com.example.demo.domain.gallery;

import com.example.demo.aws.s3.AwsS3;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Photo {
    private Long id;
    private List<AwsS3> awsS3List;
    private String title;
    private String description;

    public Photo() {
    }

    @Builder
    public Photo(List<AwsS3> awsS3List, String title, String description) {
        this.awsS3List = awsS3List;
        this.title = title;
        this.description = description;
    }
}
