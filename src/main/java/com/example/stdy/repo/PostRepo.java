package com.example.stdy.repo;

import com.example.stdy.modela.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {

}
