package com.example.demo.repository;

import com.example.demo.entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VideoRepository extends CrudRepository<VideoEntity, UUID> {
}
