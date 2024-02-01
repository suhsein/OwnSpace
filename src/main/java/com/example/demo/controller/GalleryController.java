package com.example.demo.controller;

import com.example.demo.aws.s3.AwsS3;
import com.example.demo.aws.s3.AwsS3Service;
import com.example.demo.domain.gallery.Photo;
import com.example.demo.domain.gallery.PhotoDto;
import com.example.demo.domain.gallery.PhotoRepository;
import com.example.demo.domain.gallery.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/gallery")
public class GalleryController {
    private final PhotoRepository photoRepository;
    private final AwsS3Service awsS3Service;
    private final PhotoService photoService;

    @GetMapping
    public String gallery(Model model) {
        List<Photo> photos = photoRepository.findAll();
        model.addAttribute("photos", photos);
        return "/gallery/gallery";
    }

    /**
     * CREATE
     */
    @GetMapping("/addPhoto")
    public String photoForm(@ModelAttribute("form") PhotoDto form){
        return "/gallery/add-photo";
    }

    @PostMapping("/addPhoto")
    public String addPhoto(@Validated @ModelAttribute("form") PhotoDto form,
                           BindingResult bindingResult) throws IOException {
        log.info("imageFiles={}, size={}", form.getImageFiles(), form.getImageFiles().size());
        List<MultipartFile> imageFiles = form.getImageFiles();
        for (MultipartFile imageFile : imageFiles) {
            log.info("imageFile={}", imageFile);
        }

        if(form.getImageFiles().size() == 0){
            bindingResult.reject("noFile");
            return "/gallery/add-photo";
        }

        List<AwsS3> awsS3List = photoService.uploadFiles(form);

        Photo photo = new Photo().builder()
                .title(form.getTitle())
                .awsS3List(awsS3List)
                .description(form.getDescription()).build();
        photoRepository.save(photo);
        return "redirect:/gallery";
    }

    /**
     * READ
     */

    @GetMapping("/photoView/{id}")
    public String photoView(@PathVariable("id") Long id, Model model) {
        Photo photo = photoRepository.findById(id);
        model.addAttribute("photo", photo);
        return "/gallery/photo-view";
    }

    /**
     * UPDATE
     */
    @GetMapping("/edit/{id}")
    public String editForm(@ModelAttribute("form") PhotoDto form,
                            @PathVariable("id") Long id){
        return "/gallery/edit-photo";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Validated @ModelAttribute("form") PhotoDto form,
                       BindingResult bindingResult,
                       @PathVariable("id") Long id,
                       @RequestParam(required = false) List<MultipartFile> files,
                       @RequestParam(required = false) List<Long> deleteFilesId) throws IOException {

        if(form.getImageFiles().isEmpty() || form.getImageFiles() == null){
            bindingResult.reject("noFile");
            return "/gallery/edit-photo";
        }
        photoService.editPhoto(form, id, files, deleteFilesId);
        return "redirect:/gallery";
    }

    /**
     * DELETE
     */
    @GetMapping("/delete/{id}")
    public String deletePhoto(@PathVariable("id") Long id){
        Photo photo = photoRepository.findById(id);
        photoService.removeFiles(photo);
        photoRepository.deleteById(id);
        // storage 와 repository 양쪽 다 지워주기
        return "redirect:/gallery";
    }

    @ResponseBody
    @GetMapping("/files/{id}")
    public List<AwsS3> uploadedFile(@PathVariable("id") Long id) {
        Photo photo = photoRepository.findById(id);
        return photo.getAwsS3List();
    }
}
