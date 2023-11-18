package com.example.demo.controller;

import com.example.demo.dto.CreateVideoBackInfo;
import com.example.demo.dto.UpdateVideoResolutionDTO;
import com.example.demo.dto.UpdateOrDeleteVideoResult;
import com.example.demo.dto.VideoDTO;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.impl.VideoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/file")
@Api(description = "Тестовый контроллер")
public class VideoController {
    @Autowired
    private final VideoServiceImpl videoService;

    public VideoController(VideoServiceImpl videoService) {
        this.videoService = videoService;
    }


    @PostMapping
    @ApiOperation("Тестовый метод")
    public ResponseEntity<CreateVideoBackInfo> uploadVideo(@RequestPart MultipartFile video) {
        CreateVideoBackInfo backInfo = videoService.uploadVideo(video);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(backInfo);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateVideoResolution(@RequestBody UpdateVideoResolutionDTO updateVideoResolutionDTO,@PathVariable UUID id) {
        UpdateOrDeleteVideoResult updateVideoResolutionResult = new UpdateOrDeleteVideoResult();

        try {
            updateVideoResolutionResult = videoService.updateVideoResolution(updateVideoResolutionDTO, id);
        } catch (NotFoundException notFoundException) {
            Map<String, String> errorResponseNotFound = new HashMap<>();
            errorResponseNotFound.put("error", notFoundException.getMessage());
            return new ResponseEntity<>(errorResponseNotFound, HttpStatus.NOT_FOUND);
        } catch (IOException ioException) {
            Map<String, String> errorResponseIo = new HashMap<>();
            errorResponseIo.put("error", ioException.getMessage());
            return new ResponseEntity<>(errorResponseIo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(updateVideoResolutionResult);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable UUID id) {
        try {
            VideoDTO videoDTO = videoService.getVideoById(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(videoDTO);
        } catch (NotFoundException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable UUID id){
        try {
            UpdateOrDeleteVideoResult result = videoService.deleteVideo(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(result);
        } catch (NotFoundException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}