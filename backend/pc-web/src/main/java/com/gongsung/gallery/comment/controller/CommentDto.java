package com.gongsung.gallery.comment.controller;

import com.gongsung.gallery.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CommentDto {

    private String author;
    private String content;

    public CommentDto(){}

    public CommentDto(Comment c) {
        this.author = c.getAuthor();
        this.content = c.getContent();
    }
}