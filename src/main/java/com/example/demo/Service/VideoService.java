package com.example.demo.Service;

import com.example.demo.DTO.CreateVideoBackInfo;
import com.example.demo.DTO.UpdateOrDeleteVideoResult;
import com.example.demo.DTO.UpdateVideoResolutionDTO;
import com.example.demo.DTO.VideoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface VideoService {
    CreateVideoBackInfo uploadVideo(MultipartFile video);
    VideoDTO getVideoById(UUID id);
    UpdateOrDeleteVideoResult updateVideoResolution(UpdateVideoResolutionDTO request, UUID id) throws IOException;
    UpdateOrDeleteVideoResult deleteVideo(UUID id);
}
