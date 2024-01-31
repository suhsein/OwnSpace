package com.example.demo.controller;

import com.example.demo.aws.s3.AwsS3;
import com.example.demo.aws.s3.AwsS3Service;
import com.example.demo.domain.gallery.Photo;
import com.example.demo.domain.gallery.PhotoDto;
import com.example.demo.domain.gallery.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/gallery")
public class GalleryController {
    private final PhotoRepository photoRepository;
    private final AwsS3Service awsS3Service;

    @GetMapping
    public String gallery(Model model) {
        List<Photo> photos = photoRepository.findAll();
        model.addAttribute("photos", photos);
        return "/gallery/gallery";
    }

    @GetMapping("/addPhoto")
    public String photoForm(@ModelAttribute("form") PhotoDto form){
        return "/gallery/add-photo";
    }

    @PostMapping("/addPhoto")
    public String addPhoto(@Validated @ModelAttribute("form") PhotoDto form,
                           BindingResult bindingResult) throws IOException {
        if(form.getImageFile().getOriginalFilename().isEmpty() ||
            form.getImageFile().getOriginalFilename() == null){
            bindingResult.reject("noFile");
            return "/gallery/add-photo";
        }
        AwsS3 awsS3 = awsS3Service.upload(form.getImageFile(), "upload");
        Photo photo = new Photo().builder()
                .title(form.getTitle())
                .awsS3(awsS3)
                .description(form.getDescription()).build();
        photoRepository.save(photo);
        return "redirect:/gallery";
    }

    @GetMapping("/photoView/{id}")
    public String photoView(@PathVariable Long id, Model model) {
        Photo photo = photoRepository.findById(id);
        model.addAttribute("photo", photo);
        return "/gallery/photo-view";
    }
}
