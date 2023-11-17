package com.example.demo.Repository;

import com.example.demo.Entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends CrudRepository<VideoEntity,UUID> {
}
