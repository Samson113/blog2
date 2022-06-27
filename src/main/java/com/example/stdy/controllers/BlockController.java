package com.example.stdy.controllers;

import com.example.stdy.modela.Post;
import com.example.stdy.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlockController {

    @Autowired
    private PostRepo postRepo;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anonce, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anonce, full_text);
        postRepo.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDet(@PathVariable(value = "id") long id, Model model) {
        if(!postRepo.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-det";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepo.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpd(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anonce, @RequestParam String full_text, Model model) {
        Post post = postRepo.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnonce(anonce);
        post.setFull_text(full_text);
        postRepo.save(post);
        return "redirect:/blog";
    }
    @PostMapping("/blog/{id}/remove")
    public String blogPostDel(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepo.findById(id).orElseThrow();
        postRepo.delete(post);
        return "redirect:/blog";
    }
}
