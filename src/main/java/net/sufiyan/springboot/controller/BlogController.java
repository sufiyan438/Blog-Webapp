package net.sufiyan.springboot.controller;

import net.sufiyan.springboot.dto.CommentDto;
import net.sufiyan.springboot.dto.PostDto;
import net.sufiyan.springboot.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {
    private PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String viewBlogPosts(Model model){
        List<PostDto> postResponse = postService.findAllPosts();
        model.addAttribute("postResponse", postResponse);
        return "blog/view_posts";
    }

    @GetMapping("/post/{postUrl}")
    public String showPost(@PathVariable("postUrl") String postUrl, Model model){
        PostDto post = postService.findPostByUrl(postUrl);
        CommentDto commentDto = new CommentDto();
        model.addAttribute("post", post);
        model.addAttribute("comment", commentDto);
        return "blog/blog_post";
    }

    @GetMapping("/page/search")
    public String searchPosts(@RequestParam(value = "query") String query, Model model){
        List<PostDto> postResponse = postService.searchPosts(query);
        model.addAttribute("postResponse", postResponse);
        return "blog/view_posts";
    }
}
