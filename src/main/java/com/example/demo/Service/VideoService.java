package com.example.demo.Service;

import com.example.demo.Repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Long uploadVideo() {
        return 1L;
    }


}