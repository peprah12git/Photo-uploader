package org.example.photouploader.controller;

import lombok.RequiredArgsConstructor;
import org.example.photouploader.model.Photo;
import org.example.photouploader.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<Photo> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) throws IOException {
        return ResponseEntity.ok(photoService.uploadPhoto(file, description));
    }

    @GetMapping
    public ResponseEntity<List<Photo>> getAll() {
        return ResponseEntity.ok(photoService.getAllPhotos());
    }
}