package com.example.demo.service.impl;

import com.example.demo.dto.CreateVideoBackInfo;
import com.example.demo.dto.UpdateOrDeleteVideoResult;
import com.example.demo.dto.UpdateVideoResolutionDTO;
import com.example.demo.dto.VideoDTO;
import com.example.demo.entity.VideoEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.VideoMapper;
import com.example.demo.repository.VideoRepository;
import com.example.demo.service.VideoService;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {
    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public VideoServiceImpl(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    public CreateVideoBackInfo uploadVideo(MultipartFile video) {
        return videoMapper.addVideoBackInfo(videoRepository.save(videoMapper.addVideo(video)));
    }

    public VideoDTO getVideoById(UUID id) {
        return videoMapper.toVideoDTO(videoRepository.findById(id).orElseThrow(() -> new NotFoundException("Video with id=" + id + " doesn't found.")));
    }

    public UpdateOrDeleteVideoResult updateVideoResolution(UpdateVideoResolutionDTO request, UUID id) throws IOException {
        VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(() -> new NotFoundException("Video with id=" + id + " doesn't found."));
        byte[] videoData = videoEntity.getVideo();
        String videoFilePath = "F:/videos/input.mp4";
        Files.write(Paths.get(videoFilePath), videoData);
        FFmpeg ffmpeg = new FFmpeg("F:/ffmpeg/bin/ffmpeg.exe");
        FFprobe ffprobe = new FFprobe("F:/ffmpeg/bin/ffprobe.exe");
        FFmpegProbeResult probeResult = ffprobe.probe(videoFilePath);
        FFmpegBuilder builder = new FFmpegBuilder().setInput(probeResult).overrideOutputFiles(true).addOutput("F:/videos/output.mp4").setFormat("mp4").setVideoResolution(request.getWidth(), request.getHeight()).setStrict(FFmpegBuilder.Strict.EXPERIMENTAL).done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
        videoEntity.setProcessing(true);
        videoRepository.save(videoEntity);
        UpdateOrDeleteVideoResult updateOrDeleteVideoResult = new UpdateOrDeleteVideoResult();
        File videoFile = new File("F:/videos/output.mp4");
        try (FileInputStream fis = new FileInputStream(videoFile)) {
            videoData = new byte[(int) videoFile.length()];
            fis.read(videoData);
            videoEntity = videoMapper.updateVideoResolution(videoEntity, true, videoData);
            videoRepository.save(videoEntity);
            updateOrDeleteVideoResult.setSuccess(true);
        } catch (IOException e) {
            updateOrDeleteVideoResult.setSuccess(false);
            throw e;
        }
        return updateOrDeleteVideoResult;
    }

    @Override
    public UpdateOrDeleteVideoResult deleteVideo(UUID id) {
        VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(() -> new NotFoundException("Video with id=" + id + " doesn't found."));
        videoRepository.delete(videoEntity);
        UpdateOrDeleteVideoResult updateOrDeleteVideoResult = new UpdateOrDeleteVideoResult();
        updateOrDeleteVideoResult.setSuccess(true);
        return updateOrDeleteVideoResult;
    }
}