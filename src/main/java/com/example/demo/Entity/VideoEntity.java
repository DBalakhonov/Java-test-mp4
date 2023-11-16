package com.example.demo.Entity;



import jakarta.persistence.*;

@Entity
@Table(name = "videos")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public VideoEntity() {
    }

    public VideoEntity(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
