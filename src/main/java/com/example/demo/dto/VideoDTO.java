package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class VideoDTO {
    private UUID id;
    private String filename;
    private Boolean processing;
    private Boolean processingSuccess;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getProcessing() {
        return processing;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    @Override
    public String toString() {
        return "VideoDTO{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", processing=" + processing +
                ", processingSuccess=" + processingSuccess +
                '}';
    }
}
