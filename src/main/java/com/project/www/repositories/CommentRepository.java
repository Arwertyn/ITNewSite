package com.project.www.repositories;

import com.project.www.entity.Comment;
import com.project.www.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> getCommentByPostOrderByIdDesc(Post post);

    Comment getCommentByText(String text);

    @Transactional
    void removeAllByPost(Post post);
}
