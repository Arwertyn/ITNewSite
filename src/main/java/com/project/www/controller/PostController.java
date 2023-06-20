package com.project.www.controller;

import com.project.www.entity.Post;
import com.project.www.entity.auth.User;
import com.project.www.services.UserService;
import com.project.www.services.CommentService;
import com.project.www.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("api/post/")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);
    private PostService service;
    private CommentService commentService;
    private UserService userService;

    @Autowired
    public PostController(PostService service, CommentService commentService, UserService userService) {
        this.service = service;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public List<Post> getPostList() {
        List<Post> posts = service.getAllPost();
        return posts;
    }

    @PostMapping(value = "/addPostBody")
    public void addPost(HttpServletResponse response,@RequestParam(name = "id") long id, @RequestParam(name = "json") String json) throws IOException {
        Post post = service.getPost(id);
        service.updateNode(post,json);
        response.sendRedirect("../../post/"+id);
    }

    @PostMapping(value = "/createPost")
    public void createPost(HttpServletResponse response,@RequestParam(name = "name") String name, @RequestParam(name = "preview") MultipartFile file, Authentication auth) throws IOException {
        if (service.findByName(name).isEmpty()) {
            logger.info("Create post | " + name);
            service.createPost(name, file.getBytes(), userService.findUserByUsername(auth.getName()));
        }
        response.sendRedirect("../../myPosts");
    }

    //TODO Пеместить в комменты
    @PostMapping(value = "/addComment")
    public void addComment(@RequestParam(name = "postId") long id, @RequestParam(name = "comment") String text, Authentication auth) {
        Post post = service.getPost(id);
        User user = userService.findUserByUsername(auth.getName());

        commentService.createComment(text,post,user);
    }

    @PostMapping(value = "/publish")
    public void publish(@RequestParam(name = "postId") long id, @RequestParam(name = "bool") boolean action) {
        service.updatePublication(id,action);
    }

    @PostMapping(value = "/removePost")
    public void removePost(HttpServletResponse response, @RequestParam(name = "id") long id, Authentication authentication) throws IOException {
        User user = userService.findUserByUsername(authentication.getName());
        Post delPost = service.getPost(id);
        commentService.removeAllCommentsByPost(delPost);
        service.deletePost(id);


        response.sendRedirect("../../myPosts");
    }

    @GetMapping(value = "getByName")
    public String getPost(@RequestParam(name = "name") String name) {
        Optional<Post> optional = service.findByName(name);

        if (optional.isPresent()) {
            logger.info("GET post | " + optional.get().getName());
            return optional.get().getName();
        }
        return "";
    }

    @PostMapping(value = "/updateHeaders")
    public void updateHead(HttpServletResponse response, @RequestParam(name = "name") String name, @RequestParam(name = "preview") MultipartFile file, @RequestParam(name = "postId") long id){
        try{
            service.updateHeaders(id,name,file.getBytes());
            response.sendRedirect("../../myPosts");
        } catch (IOException exception){
            exception.printStackTrace();
        }

    }



}
