package com.project.www.controller;

import com.project.www.entity.Comment;
import com.project.www.entity.Post;
import com.project.www.entity.auth.User;
import com.project.www.services.CommentService;
import com.project.www.services.PostService;
import com.project.www.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment/")
public class CommentController {

    private CommentService service;
    private PostService postService;
    private UserService userService;

    @Autowired
    public CommentController(CommentService service, PostService postService, UserService userService) {
        this.service = service;
        this.postService = postService;
        this.userService = userService;
    }


    @GetMapping(value = "/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable(name = "postId") long postId) {
       Post post = postService.getPost(postId);
       return service.getAllCommentByPost(post);
    }

    @PostMapping(value = "/delete")
    public void deleteComment(@RequestParam(name = "id") Long id) {
        service.deleteComment(id);
    }

    @PostMapping(value = "/update")
    public void updateComment(@RequestParam(name = "id") Long id, @RequestParam(name = "text") String text) {
        service.updateComment(id,text);
    }

    @PostMapping(value = "/addComment")
    public void addComment(@RequestParam(name = "postId") long id, @RequestParam(name = "comment") String text, Authentication auth) {
        Post post = postService.getPost(id);
        User user = userService.findUserByUsername(auth.getName());

        service.createComment(text,post,user);
    }

    @GetMapping(value = "/addComment")
    public String test() {
        return "test";
    }

}
