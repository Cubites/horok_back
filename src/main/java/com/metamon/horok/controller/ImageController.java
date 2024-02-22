package com.metamon.horok.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Value("${upload.path}")
    private String path;

    @GetMapping("/show/image")
    public ResponseEntity<?> returnImage(@RequestParam(name = "imageName") String imageName) {
        Resource resource = new FileSystemResource(path + imageName);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}