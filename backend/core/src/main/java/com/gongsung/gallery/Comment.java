package com.gongsung.gallery;


import domain.BaseTimeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String author;

    //private LocalDateTime writtenAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
        //writtenAt = LocalDateTime.now();
    }

    public void updateContent(String content){
        this.content = content;
    }

    public static Comment createComment(Board board, String author, String content){
        Comment comment = new Comment(author, content);
        comment.setComment(board);
        return comment;
    }

    public void setComment(Board board){
        this.board = board;
        board.getComments().add(this);
    }
}