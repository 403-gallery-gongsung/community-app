package com.gongsung.gallery;



import javax.persistence.*;

@Entity
public class Comment {


    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private String author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}