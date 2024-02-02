package com.example.demo.controller.gallery;

import com.example.demo.domain.s3.AwsS3;
import com.example.demo.domain.gallery.Photo;
import com.example.demo.domain.gallery.PhotoDto;
import com.example.demo.repository.gallery.PhotoRepository;
import com.example.demo.service.gallery.PhotoService;
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
@RequestMapping("/gallery")
public class GalleryController {
    private final PhotoRepository photoRepository;
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
        List<MultipartFile> imageFiles = form.getImageFiles();

        if(photoService.checkNull(imageFiles)){
            bindingResult.reject("noFile");
            return "/gallery/add-photo";
        }

        List<AwsS3> awsS3List = photoService.uploadFiles(imageFiles);

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
    public String editForm(@PathVariable("id") Long id,
                           Model model){
        Photo photo = photoRepository.findById(id);
        PhotoDto form = new PhotoDto();
        form.setTitle(photo.getTitle());
        form.setDescription(photo.getDescription());
        model.addAttribute("form", form);
        return "/gallery/edit-photo";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Validated @ModelAttribute("form") PhotoDto form,
                       BindingResult bindingResult,
                       @PathVariable("id") Long id,
                       @RequestParam(required = false, value="deleteFilesId") List<Long> deleteFilesId) throws IOException {
        List<MultipartFile> imageFiles = form.getImageFiles();
        Photo photo = photoRepository.findById(id);

        if(photoService.checkNull(imageFiles) && deleteFilesId != null && deleteFilesId.size() == photo.getAwsS3List().size()){
            bindingResult.reject("noFile");
            return "/gallery/edit-photo";
        }

        photoService.editPhoto(form, id, imageFiles, deleteFilesId);
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


    /**
     * API
     */
    @ResponseBody
    @GetMapping("/files/{id}")
    public List<AwsS3> uploadedFile(@PathVariable("id") Long id) {
        Photo photo = photoRepository.findById(id);
        return photo.getAwsS3List();
    }
}
