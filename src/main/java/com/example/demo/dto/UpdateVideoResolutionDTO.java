package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Data
@Accessors(chain = true)
public class UpdateVideoResolutionDTO {
    @Min(20)
    private int width;
    @Min(20)
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
