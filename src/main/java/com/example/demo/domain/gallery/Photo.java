package com.example.demo.domain.gallery;

import com.example.demo.domain.photo.UploadFile;
import lombok.Data;

@Data
public class Photo {
    private Long id;
    private UploadFile imageFile;
    private String description;
}
