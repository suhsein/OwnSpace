package com.example.demo.controller;

import com.example.demo.domain.gallery.Photo;
import com.example.demo.domain.gallery.PhotoDto;
import com.example.demo.domain.gallery.PhotoRepository;
import com.example.demo.domain.photo.FileStore;
import com.example.demo.domain.photo.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/gallery")
public class GalleryController {
    private final PhotoRepository photoRepository;
    private final FileStore fileStore;
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
        Photo photo = new Photo();
        UploadFile imageFile = fileStore.storeFile(form.getImageFile());
        photo.setImageFile(imageFile);
        photo.setTitle(form.getTitle());
        photo.setDescription(form.getDescription());
        photoRepository.save(photo);
        return "redirect:/gallery";
    }


    @ResponseBody
    @GetMapping("/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/photoView/{id}")
    public String photoView(@PathVariable Long id, Model model) {
        Photo photo = photoRepository.findById(id);
        model.addAttribute("photo", photo);
        return "/gallery/photo-view";
    }
}
