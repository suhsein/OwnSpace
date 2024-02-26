package com.suhsein.ownspace.controller.gallery;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PhotoDto {
    private List<MultipartFile> imageFiles;
    private String title;
    private String content;
}
