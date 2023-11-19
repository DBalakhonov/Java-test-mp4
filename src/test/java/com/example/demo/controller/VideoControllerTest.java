package com.example.demo.controller;

import com.example.demo.dto.UpdateVideoResolutionDTO;
import com.example.demo.dto.VideoDTO;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.VideoMapper;
import com.example.demo.repository.VideoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Добавление видео тест")
    void uploadVideoTest() throws Exception {
        long countBefore = videoRepository.count() + 1;
        MockMultipartFile videoFile = new MockMultipartFile("video", "F:/videos/testvideo.,mp4", MediaType.APPLICATION_OCTET_STREAM_VALUE, "mockPseudoValue".getBytes());

        mockMvc.perform(multipart("/file").file(videoFile))
                .andExpect(status().isOk());
        long countAfter = videoRepository.count();
        assertEquals(countBefore, countAfter);
    }

    @Test
    @DisplayName("Получение видео по ID")
    void getVideoTest() throws Exception {
        String uuidPart = null;
        MockMultipartFile videoFile = new MockMultipartFile("video", "F:/videos/testvideo.,mp4", MediaType.APPLICATION_OCTET_STREAM_VALUE, "mockPseudoValue".getBytes());

        MvcResult result = mockMvc.perform(multipart("/file").file(videoFile))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        String[] parts = content.split(":");
        if (parts.length >= 2) {
            uuidPart = parts[1].replaceAll("[^a-zA-Z0-9-]", "");
        }
        UUID id = UUID.fromString(uuidPart);

        VideoDTO videoDTO = videoMapper.toVideoDTO(videoRepository.findById(id).orElseThrow(() -> new NotFoundException("Video with id=" + id + " doesn't found.")));
        String currentVideo = objectMapper.writeValueAsString(videoDTO);
        mockMvc.perform(get("/file/{id}", id))
                .andExpect(content().json(currentVideo))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление видео по ID")
    void deleteVideoTest() throws Exception {
        String uuidPart = null;
        MockMultipartFile videoFile = new MockMultipartFile("video", "F:/videos/testvideo.,mp4", MediaType.APPLICATION_OCTET_STREAM_VALUE, "mockPseudoValue".getBytes());

        MvcResult result = mockMvc.perform(multipart("/file").file(videoFile))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        String[] parts = content.split(":");
        if (parts.length >= 2) {
            uuidPart = parts[1].replaceAll("[^a-zA-Z0-9-]", "");
        }
        UUID id = UUID.fromString(uuidPart);

        long beforeCount = videoRepository.count();
        mockMvc.perform(delete("/file/{id}", id))
                .andExpect(status().isOk());
        long afterCount = videoRepository.count();
        assertEquals(beforeCount - 1, afterCount);
    }

    @Test
    @DisplayName("Обновление разрешения видео")
    void updateResplutinVideoTest() throws Exception {
        String uuidPart = null;
        Path videoFilePath = Paths.get(getClass().getResource("/testvideo.mp4").toURI());
        byte[] videoBytes = Files.readAllBytes(videoFilePath);
        MockMultipartFile videoFile = new MockMultipartFile(
                "video",
                "testvideo.mp4",
                "video/mp4",
                videoBytes
        );

        MvcResult result = mockMvc.perform(multipart("/file").file(videoFile))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String[] parts = content.split(":");
        if (parts.length >= 2) {
            uuidPart = parts[1].replaceAll("[^a-zA-Z0-9-]", "");
        }
        UUID id = UUID.fromString(uuidPart);

        UpdateVideoResolutionDTO updateVideoResolutionDTO = new UpdateVideoResolutionDTO();
        updateVideoResolutionDTO.setHeight(22);
        updateVideoResolutionDTO.setWidth(22);
        String json = objectMapper.writeValueAsString(updateVideoResolutionDTO);
        mockMvc.perform(patch("/file/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }
}
