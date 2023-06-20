package com.project.www.services;

import com.project.www.entity.Comment;
import com.project.www.entity.Post;
import com.project.www.entity.auth.User;
import com.project.www.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository repository;
    private UserService userService;

    @Autowired
    public PostService(PostRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void createPost(String name, byte[] preview, User user) {
        Post post = new Post();
        post.setName(name);
        post.setPreview(preview);
        post.setDate(new Date());
        post.setCommentList(new ArrayList<>());
        post.setUser(user);
        repository.save(post);
    }

    public List<Post> getAllPost() {
        return repository.findAll();
    }

    public Post getPost(long id) {
        return repository.findById(id).get();
    }

    public void addComment(Long postId, Comment comment) {
        Optional<Post> optional = repository.findById(postId);
        if (optional.isPresent()) {
            Post post = optional.get();
            post.getCommentList().add(comment);
            repository.save(post);
        }
    }


//    public void updateComment(long postId,String text) {
//        if(repository.findById(commentId).isPresent()) {
//            Comment currentComment = repository.findById(commentId).get();
//            currentComment.setText(text);
//            repository.save(currentComment);
//        }
//    }

    public void updateNode(Post post, String node) {
        post.setNode(node);
        repository.save(post);
    }

    public void deletePost(Long postId) {
        Optional<Post> optional = repository.findById(postId);
        if (optional.isPresent()) {
            repository.deleteById(postId);
        }
    }

    public Optional<Post> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Post> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    public void updatePublication(long id, boolean flag) {
        Post post = getPost(id);
        post.setPublished(flag);
        repository.save(post);
    }

    public void updateHeaders(long id, String name, byte[] file) {
        Post post = getPost(id);
        post.setName(name);
        post.setPreview(file);
        repository.save(post);
    }

    public List<Post> getAllPublishedPost() {
        return repository.findAllByPublishedOrderByIdDesc(true);
    }

    public List<Post> getAllPublishedPostLimitSix() {
        List<Post> posts = repository.findAllByPublishedOrderByIdDesc(true);
        List<Post> fivePosts = new ArrayList<>();
        if (posts.size() < 6) {
            posts.forEach(post -> fivePosts.add(post));
        } else {
            for (int i = 0; i < 6; i++) fivePosts.add(posts.get(i));
        }
        return fivePosts;
    }
}
