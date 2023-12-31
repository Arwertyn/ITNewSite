package com.project.www.repositories;

import com.project.www.entity.Post;
import com.project.www.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByName(String name);
    List<Post> findAllByUser(User user);

    List<Post> findAllByPublishedOrderByIdDesc(boolean isPublished);
}
