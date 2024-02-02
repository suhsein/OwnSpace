package com.example.demo.domain.s3;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsS3 {
    private Long id;
    private String originalName; // edit-photo 에서 보여줄 이름
    private String key;
    private String path;
    public AwsS3(){
    }
    @Builder
    public AwsS3(String originalName, String key, String path) {
        this.originalName = originalName;
        this.key = key;
        this.path = path;
    }
}
