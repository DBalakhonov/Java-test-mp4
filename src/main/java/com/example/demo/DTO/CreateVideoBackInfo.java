package com.example.demo.DTO;

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
}
