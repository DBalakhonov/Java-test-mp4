package com.example.demo.mapper;

import com.example.demo.DTO.CreateVideoBackInfo;
import com.example.demo.DTO.VideoDTO;
import com.example.demo.Entity.VideoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class VideoMapper {
    private static final Logger log  = LoggerFactory.getLogger(VideoMapper.class);
    public VideoEntity addVideo(MultipartFile video) {
        VideoEntity videoEntity = new VideoEntity();
        try {
            videoEntity.setId(UUID.randomUUID());
            videoEntity.setVideo(video.getBytes());
            videoEntity.setProcessing(false);
            videoEntity.setProcessingSuccess(null);
            videoEntity.setFilename(video.getOriginalFilename());
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return videoEntity;
    }
    public CreateVideoBackInfo addVideoBackInfo(VideoEntity videoEntity){
        CreateVideoBackInfo createVideoBack = new CreateVideoBackInfo();
        createVideoBack.setId(videoEntity.getId());
        return createVideoBack;
    }
    public VideoDTO toVideoDTO(VideoEntity videoEntity){
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(videoEntity.getId());
        videoDTO.setFilename(videoEntity.getFilename());
        videoDTO.setProcessing(videoEntity.getProcessing());
        videoDTO.setProcessingSuccess(videoEntity.getProcessingSuccess());
        return videoDTO;
    }
    public VideoEntity updateVideoResolution(VideoEntity videoEntity,Boolean result,byte[] videoData){
        videoEntity.setVideo(videoData);
        videoEntity.setProcessingSuccess(result);
        videoEntity.setProcessing(false);
        return videoEntity;
    }
}
