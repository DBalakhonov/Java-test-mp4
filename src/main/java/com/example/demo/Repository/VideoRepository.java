package com.example.demo.Repository;

import com.example.demo.Entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<VideoEntity,Long> {
}
