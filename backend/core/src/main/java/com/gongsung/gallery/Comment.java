package com.gongsung.gallery;


import com.fasterxml.jackson.databind.ser.Serializers.Base;
import domain.BaseTimeEntity;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies;

    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
        this.setCreatedDate(LocalDateTime.now());
    }

    @Transactional
    public void updateContent(String content){
        this.content = content;
        this.setModifiedDate(LocalDateTime.now());
    }

    public static Comment createComment(Board board, String author, String content){
        Comment comment = new Comment(author, content);
        comment.setComment(board);
        comment.setCreatedDate(LocalDateTime.now());
        return comment;
    }

    public void setComment(Board board){
        this.board = board;
        board.getComments().add(this);
    }
}