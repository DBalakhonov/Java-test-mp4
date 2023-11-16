package com.example.demo.Controller;

import com.example.demo.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class VideoController {
    @Autowired
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    @PostMapping
    public ResponseEntity<Long> uploadVideo(@RequestPart MultipartFile video) {
        return null;
    }
}