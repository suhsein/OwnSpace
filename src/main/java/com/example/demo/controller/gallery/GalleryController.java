package com.example.demo.controller.gallery;

import com.example.demo.domain.s3.AwsS3;
import com.example.demo.domain.gallery.Photo;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gallery")
@Slf4j
public class GalleryController {
    private final PhotoService photoService;

    @GetMapping
    public String gallery(Model model) {
        List<Photo> photos = photoService.findAll();
        model.addAttribute("photos", photos);
        return "/gallery/gallery";
    }

    /**
     *
     * CREATE
     */
    @GetMapping("/addPhoto")
    public String createPhotoForm(@ModelAttribute("form") PhotoDto form){
        return "/gallery/add-photo";
    }

    @PostMapping("/addPhoto")
    public String createPhoto(@Validated @ModelAttribute("form") PhotoDto form,
                           BindingResult bindingResult) throws IOException {
        List<MultipartFile> imageFiles = form.getImageFiles();

        if(photoService.checkNull(imageFiles)){
            bindingResult.reject("noFile");
            return "/gallery/add-photo";
        }

        List<AwsS3> awsS3List = photoService.uploadFiles(imageFiles);

        Photo photo = Photo.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .awsS3List(awsS3List).build();

        photoService.save(photo);
        return "redirect:/gallery";
    }

    /**
     * READ
     */

    @GetMapping("/photoView/{id}")
    public String photoView(@PathVariable("id") Long id, Model model) {
        Photo photo = photoService.findOne(id);
        photo.setContent(photo.getContent().replace("\r\n","<br>"));
        // 출력 시에 \r\n을 <br>로 바꿔서 출력. 타임리프 unescaped text 로 출력
        model.addAttribute("photo", photo);
        return "/gallery/photo-view";
    }

    /**
     * UPDATE
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id,
                           Model model){
        Photo photo = photoService.findOne(id);
        PhotoDto form = new PhotoDto();
        form.setTitle(photo.getTitle());
        form.setContent(photo.getContent());
        model.addAttribute("form", form);
        return "/gallery/edit-photo";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Validated @ModelAttribute("form") PhotoDto form,
                       BindingResult bindingResult,
                       @PathVariable("id") Long id,
                       @RequestParam(required = false, value="deleteFilesId") List<Long> deleteFilesId) throws IOException {
        List<MultipartFile> imageFiles = form.getImageFiles();
        Photo photo = photoService.findOne(id);

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
        photoService.remove(id);
        return "redirect:/gallery";
    }

    /**
     * API
     */
    @ResponseBody
    @GetMapping("/files/{id}")
    public List<AwsS3Dto> list(@PathVariable("id") Long id) {
        Photo photo = photoService.findOne(id);
        List<AwsS3> awsS3List = photo.getAwsS3List();
        // Infinite recursion 문제로 엔티티 대신 DTO 리스트 반환
        List<AwsS3Dto> awsS3DtoList = new ArrayList<>();
        for (AwsS3 awsS3 : awsS3List) {
            AwsS3Dto awsS3Dto = AwsS3Dto.builder()
                    .id(awsS3.getId())
                    .originalName(awsS3.getOriginalName())
                    .build();
            awsS3DtoList.add(awsS3Dto);
        }
        return awsS3DtoList;
    }
}
