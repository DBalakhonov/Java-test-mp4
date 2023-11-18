package com.example.demo.service;

import com.example.demo.dto.CreateVideoBackInfo;
import com.example.demo.dto.UpdateOrDeleteVideoResult;
import com.example.demo.dto.UpdateVideoResolutionDTO;
import com.example.demo.dto.VideoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface VideoService {
    CreateVideoBackInfo uploadVideo(MultipartFile video);
    VideoDTO getVideoById(UUID id);
    UpdateOrDeleteVideoResult updateVideoResolution(UpdateVideoResolutionDTO request, UUID id) throws IOException;
    UpdateOrDeleteVideoResult deleteVideo(UUID id);
}
