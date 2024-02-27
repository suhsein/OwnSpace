package com.suhsein.ownspace.controller.gallery.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AwsS3Dto {
    private Long id;
    private String originalName;

    @Builder
    public AwsS3Dto(Long id, String originalName) {
        this.id = id;
        this.originalName = originalName;
    }
}
