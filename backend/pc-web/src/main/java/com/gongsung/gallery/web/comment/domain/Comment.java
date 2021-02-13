package com.gongsung.gallery.web.comment.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String author;

    //private LocalDateTime writtenAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(String content, String author) {
        this.content = content;
        this.author = author;
        //writtenAt = LocalDateTime.now();
    }

    public Comment() {

    }

    public void updateContent(String content){
        this.content = content;
    }
}