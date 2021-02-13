package com.gongsung.gallery.web.comment.controller;

import com.gongsung.gallery.web.comment.domain.Comment;
import com.gongsung.gallery.web.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @GetMapping("/comments")
    public String comments(Model model){
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        return "comments";
    }

}
