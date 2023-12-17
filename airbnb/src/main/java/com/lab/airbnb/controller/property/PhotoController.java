package com.lab.airbnb.controller.property;

import com.lab.airbnb.model.Photo;
import com.lab.airbnb.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController {

    @Value("${file.upload.path}")
    private String uploadPath;

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/{houseId}/upload")
    public void uploadPhoto(@PathVariable String houseId,@RequestParam("file") MultipartFile file) {
        try {
            // Define the path to save the uploaded file
            String uri = uploadPath + file.getOriginalFilename();
            System.out.println(uri);
            Path path = Paths.get(uri);
            File destFile = path.toFile();

            // Save the uploaded file to the defined path
            file.transferTo(destFile);

            // Save the file path to the database
            photoService.save(houseId,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{houseId}/Photo")
    public ResponseEntity<List<Resource>> getPhoto(@PathVariable String houseId) {
        List<Photo> photos = photoService.getAllPhoto(houseId);

        List<Resource> resources = new ArrayList<>();

        for (Photo photo : photos) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(photo.getFileUrl()));
                resources.add(new InputStreamResource(fileInputStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resources);
    }
}