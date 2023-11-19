package com.example.demo.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "videos")
public class VideoEntity {
    @Id
    private UUID id;
    @Lob
    @Column(name = "video", nullable = false)
    private byte[] video;
    private String filename;
    @Column(name = "processing", nullable = false)
    private Boolean processing;
    @Column(name = "processingSuccess")
    private Boolean processingSuccess;


    public VideoEntity() {
    }

    public UUID getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public Boolean getProcessing() {
        return processing;
    }

    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    public Boolean getProcessingSuccess() {
        return processingSuccess;
    }

    public void setProcessingSuccess(Boolean processingSuccess) {
        this.processingSuccess = processingSuccess;
    }
}
