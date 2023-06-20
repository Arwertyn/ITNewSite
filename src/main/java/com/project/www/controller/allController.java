package com.project.www.controller;

import com.project.www.entity.Post;
import com.project.www.entity.auth.User;
import com.project.www.services.CommentService;
import com.project.www.services.ImageUtil;
import com.project.www.services.PostService;
import com.project.www.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class allController {

    private PostService postService;
    private UserService userService;
    private CommentService commentService;

    @Autowired
    public allController(PostService postService, UserService userService,CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService =commentService;
    }

    @GetMapping(value = "/")
    public ModelAndView getIndex()
    {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Post> posts = postService.getAllPublishedPostLimitSix();

        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping(value = "/allPosts")
    public ModelAndView getAllPublishedPost()
    {
        ModelAndView modelAndView = new ModelAndView("allPosts");
        List<Post> all = postService.getAllPublishedPost();

        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("posts", all);
        return modelAndView;
    }

    @GetMapping(value = "/createPost")
    public String createPost(){return "createPost";}

    @GetMapping(value = "/myPosts")
    public ModelAndView myPost(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("myPosts");
        User user = userService.findUserByUsername(authentication.getName());
        List<Post> userPosts = postService.findAllByUser(user);

        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("posts", userPosts);
        return modelAndView;
    }

    @GetMapping(value = "/post/{id}")
    public ModelAndView modelAndView(Authentication authentication, @PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("post");
        Post post = postService.getPost(id);
        User user = userService.findUserByUsername(authentication.getName());

        if(post.getUser().getId() == user.getId())
        {
            modelAndView.addObject("owner", true);
        } else  modelAndView.addObject("owner", false);

        modelAndView.addObject("post", post);
        return modelAndView;
    }




}
