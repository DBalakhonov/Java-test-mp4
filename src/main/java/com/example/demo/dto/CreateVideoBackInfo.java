package com.example.demo.dto;

import lombok.Data;
import java.util.UUID;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateVideoBackInfo {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CreateVideoBackInfo{" +
                "id=" + id +
                '}';
    }
}
