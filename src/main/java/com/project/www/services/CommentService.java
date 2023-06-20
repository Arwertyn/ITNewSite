package com.project.www.services;

import com.project.www.entity.Comment;
import com.project.www.entity.Post;
import com.project.www.entity.auth.User;
import com.project.www.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository repository;

    @Autowired
    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public Comment createComment(String text, Post post, User user) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setPost(post);
        comment.setUser(user);
        comment.setUsername(user.getUsername());
       return repository.save(comment);
    }

    public List<Comment> getAllCommentByPost(Post post) {
        return repository.getCommentByPostOrderByIdDesc(post);
    }


    public void updateComment(long commentId,String text) {
        Optional<Comment> optional = repository.findById(commentId);
        if(optional.isPresent()) {
            Comment currentComment = optional.get();
            currentComment.setText(text);
            repository.save(currentComment);
        }
    }

    public void deleteComment(Long commentId) {
        Optional<Comment> optional = repository.findById(commentId);
        if(optional.isPresent()) {
            repository.deleteById(commentId);
        }
    }

    public void removeAllCommentsByPost(Post post){
        repository.removeAllByPost(post);
    }
}
