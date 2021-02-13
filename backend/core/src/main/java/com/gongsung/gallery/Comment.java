package com.gongsung.gallery;



import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
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

    public void updateContent(String content){
        this.content = content;
    }
}